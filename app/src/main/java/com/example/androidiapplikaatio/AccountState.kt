package com.example.androidiapplikaatio

data class AccountState(
    val accounts: List<Account> = emptyList(),
    val username: String = "",
    val lastName: String = "",
    val image: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME

)
