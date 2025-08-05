package com.example.dovietha_bt.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    val playlistId:Long = 0L,
    val name:String,
    val username: String
)
