package com.example.dovietha_bt.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dovietha_bt.db.dao.MusicDao
import com.example.dovietha_bt.db.dao.MusicPlaylistCrossRefDao
import com.example.dovietha_bt.db.dao.PlaylistDao
import com.example.dovietha_bt.db.dao.UserDao
import com.example.dovietha_bt.db.entity.User

@Database(entities = [User::class], version = 2)
abstract class AppDB : RoomDatabase() {
    abstract fun UserDao() : UserDao
    abstract fun MusicDao() : MusicDao
    abstract fun PlaylistDao() : PlaylistDao
    abstract fun MusicPlaylistCrossRefDao() : MusicPlaylistCrossRefDao
    companion object{
        @Volatile
        private var INSTANCE: AppDB?=null
        fun getInstance(context: Context): AppDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java, "app_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}