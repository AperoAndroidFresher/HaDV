package com.example.dovietha_bt.database.repository.impl

import android.content.Context
import com.example.dovietha_bt.database.AppDB
import com.example.dovietha_bt.database.entity.User
import com.example.dovietha_bt.database.repository.UserRepository

class UserRepositoryImpl(context: Context): UserRepository {
    val userDao = AppDB.getInstance(context).UserDao()
    override suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    override suspend fun getUserByUsername(username: String): User? {
        return userDao.getUsersByUsername(username)
    }

    override suspend fun removeUser(user: User) {
        userDao.deleteUser(user)
    }

    override suspend fun updateUser(user: User?) {
        val existUser = userDao.getUsersByUsername(user?.userName ?: "")
        if(existUser==null){
            throw Exception("User not register")
        }
        val updateUser = existUser.copy(
            profileName = user?.profileName,
            phoneNumber = user?.phoneNumber,
            university = user?.university,
            desc = user?.desc,
            avatarUrl = user?.avatarUrl
        )
        userDao.updateUser(updateUser)
    }
}