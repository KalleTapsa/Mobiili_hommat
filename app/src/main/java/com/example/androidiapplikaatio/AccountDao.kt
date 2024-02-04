package com.example.androidiapplikaatio

import androidx.room.*
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Upsert
    suspend fun upsertAccount(account: Account)

    @Delete
    suspend fun deleteContact(account: Account)

    @Query("SELECT * FROM account ORDER BY username ASC")
    fun getContactsOrderedByFirstName(): Flow<List<Account>>

    @Query("SELECT * FROM account ORDER BY lastName ASC")
    fun getContactsOrderedByLastName(): Flow<List<Account>>

}