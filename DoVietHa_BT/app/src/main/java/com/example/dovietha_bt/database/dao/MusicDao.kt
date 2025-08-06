package com.example.dovietha_bt.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dovietha_bt.database.entity.Music
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {
    @Query("SELECT * FROM Music")
    fun getAllMusics(): Flow<List<Music>>

    @Query("SELECT * FROM music WHERE MusicId = :id")
    suspend fun getMusicsById(id: Long): Music

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMusic(music: Music): Long
}