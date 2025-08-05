package com.example.dovietha_bt.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dovietha_bt.db.entity.Music

@Dao
interface MusicDao {
    @Query("SELECT * FROM Music")
    suspend fun getAllMusics():List<Music>
    @Query("SELECT * FROM music WHERE MusicId = :id")
    suspend fun getMusicsById(id:Long): Music
}