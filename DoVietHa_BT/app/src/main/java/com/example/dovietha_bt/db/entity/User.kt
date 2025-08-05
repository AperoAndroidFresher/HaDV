package com.example.dovietha_bt.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val userName:String,
    val password:String,
    val email:String,
    val profileName:String? = null,
    val phoneNumber:String? = null,
    val university:String? = null,
    val desc:String? = null,
    val avatarUrl:String?=null
)