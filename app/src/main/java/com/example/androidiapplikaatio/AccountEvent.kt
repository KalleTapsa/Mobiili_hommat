package com.example.androidiapplikaatio

sealed interface AccountEvent {
    object SaveAccount : AccountEvent
    data class setUsername(val username: String) : AccountEvent
    data class SetLastName(val lastName: String): AccountEvent
    data class setProfilepicture(val profilePicture: String) : AccountEvent
    object ShowDialog: AccountEvent
    object HideDialog: AccountEvent
    data class SortAccounts(val sortType: SortType): AccountEvent
    data class DeleteAccount(val account: Account): AccountEvent

}