package com.example.dovietha_bt.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Duration

@Entity(tableName = "music")
data class MusicDB(
    @PrimaryKey val musicId:Long,
    val name:String,
    val author:String,
    val duration: String,
    val image:String?,
    val data:String?
)