package com.example.dovietha_bt.db.repository

import com.example.dovietha_bt.db.entity.User

interface UserRepository {
    suspend fun addUser(user: User)
    suspend fun getUserByUsername(username:String):User?
    suspend fun removeUser(user: User)
    suspend fun updateUser(user:User)
}