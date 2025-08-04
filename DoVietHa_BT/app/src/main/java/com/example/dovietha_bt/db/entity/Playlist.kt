package com.example.dovietha_bt.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dovietha_bt.myplaylist.model.Music
import org.jetbrains.annotations.NotNull

@Entity(tableName = "playlist")
data class PlaylistDB(
    @PrimaryKey val playlistId:Long,
    val name:String
)
