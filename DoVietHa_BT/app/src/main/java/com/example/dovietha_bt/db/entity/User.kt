package com.example.dovietha_bt.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val userName:String = "",
    val password:String = "",
    val email:String="",
    val profileName:String?="",
    val phoneNumber:String?="",
    val university:String?="",
    val desc:String?="",
    val avatarUrl:String?=""
)