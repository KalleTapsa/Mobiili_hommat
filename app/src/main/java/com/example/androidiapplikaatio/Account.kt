package com.example.androidiapplikaatio

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val username: String,
    val lastName: String,
    val image: String
)

