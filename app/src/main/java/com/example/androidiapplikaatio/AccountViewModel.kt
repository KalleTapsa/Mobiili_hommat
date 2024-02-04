package com.example.androidiapplikaatio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel(
    private val accountDao: AccountDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val _contacts = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.FIRST_NAME -> accountDao.getContactsOrderedByFirstName()
                SortType.LAST_NAME -> accountDao.getContactsOrderedByLastName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(AccountState())
    val state = combine(_state, _sortType, _contacts) { state, sortType, accounts ->
        state.copy(
            accounts = accounts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AccountState())

    fun onEvent(event: AccountEvent) {
        when (event) {
            AccountEvent.SaveAccount -> {
                val username = state.value.username
                val lastname = state.value.lastName
                val profilePicture = state.value.image

                if(username.isBlank() || profilePicture.isBlank() || lastname.isBlank()) {
                    return
                }

                val account = Account(
                    username = username,
                    lastName = lastname,
                    image = profilePicture
                )
                viewModelScope.launch {
                    accountDao.upsertAccount(account)
                }
                _state.update {
                    it.copy(
                        username = "",
                        image = "",
                        isAddingContact = false
                    )
                }
            }
            is AccountEvent.setUsername -> {
                _state.update {
                    it.copy(username = event.username)
                }
            }
            is AccountEvent.SetLastName -> {
                _state.update {
                    it.copy(lastName = event.lastName)
                }
            }
            is AccountEvent.setProfilepicture -> {
                _state.update {
                    it.copy(image = event.profilePicture)
                }
            }

            AccountEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingContact = false
                ) }
            }
            AccountEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingContact = true
                ) }
            }
            is AccountEvent.DeleteAccount -> {
                viewModelScope.launch {
                    accountDao.deleteContact(event.account)
                }
            }
            is AccountEvent.SortAccounts -> {
                _sortType.value = event.sortType
            }
        }
    }
}
/*
class AccountViewModel(
    private val accountDao: AccountDao
) : ViewModel() {

    private val _state = MutableStateFlow(AccountState())
    val state: StateFlow<AccountState> = _state

    fun onEvent(event: AccountEvent) {
        when (event) {
            is AccountEvent.SaveAccount -> {
                val username = event.username
                val profilePicture = event.imageUri

                if (username.isBlank() || profilePicture.isBlank()) {
                    return
                }

                val account = Account(
                    username = username,
                    image = profilePicture
                )

                viewModelScope.launch {
                    accountDao.upsertAccount(account)
                }

                // Update the state accordingly
                _state.update {
                    it.copy(
                        username = username,
                        image = profilePicture,
                        accountSaved = true
                    )
                }
            }
            is AccountEvent.setUsername -> {
                _state.update {
                    it.copy(username = event.username)
                }
            }
            is AccountEvent.setProfilepicture -> {
                _state.update {
                    it.copy(image = event.profilePicture)
                }
            }
            is AccountEvent.ShowAccountSavedConfirmation -> {
                _state.update {
                    it.copy(accountSaved = true)
                }
            }
        }
    }
}*/
