package com.example.dovietha_bt.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dovietha_bt.db.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    suspend fun getAllUsers():List<User>
    @Query("SELECT * FROM User WHERE userName = :username")
    suspend fun getUsersByUsername(username: String):User?
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)
    @Delete
    suspend fun deleteUser(user: User)
    @Update
    suspend fun updateUser(user: User)
}