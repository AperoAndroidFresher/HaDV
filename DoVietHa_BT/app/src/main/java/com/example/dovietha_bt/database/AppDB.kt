package com.example.dovietha_bt.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dovietha_bt.database.dao.MusicDao
import com.example.dovietha_bt.database.dao.MusicPlaylistCrossRefDao
import com.example.dovietha_bt.database.dao.PlaylistDao
import com.example.dovietha_bt.database.dao.UserDao
import com.example.dovietha_bt.database.entity.Music
import com.example.dovietha_bt.database.entity.MusicPlaylistCrossRef
import com.example.dovietha_bt.database.entity.Playlist
import com.example.dovietha_bt.database.entity.User

@Database(
    entities = [User::class, Music::class, Playlist::class, MusicPlaylistCrossRef::class],
    version = 1
)
abstract class AppDB : RoomDatabase() {
    abstract fun UserDao(): UserDao
    abstract fun MusicDao(): MusicDao
    abstract fun PlaylistDao(): PlaylistDao
    abstract fun MusicPlaylistCrossRefDao(): MusicPlaylistCrossRefDao

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null
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