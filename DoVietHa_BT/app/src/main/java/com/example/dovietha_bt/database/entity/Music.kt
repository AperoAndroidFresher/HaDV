package com.example.dovietha_bt.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Music(
    @PrimaryKey(autoGenerate = true)
    val musicId: Long = 0L,
    val name: String,
    val author: String,
    val duration: String,
    val image: String?
)