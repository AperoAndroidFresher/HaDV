package com.example.dovietha_bt.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dovietha_bt.db.entity.MusicDB
import com.example.dovietha_bt.myplaylist.model.Music

@Dao
interface MusicDao {
    @Query("SELECT * FROM music")
    fun getAllMusics():List<Music>
    @Query("SELECT * FROM music WHERE musicId = :id")
    fun getMusicsById(id:Long): Music
    @Insert
    fun addMusic(vararg music: Music)
    @Delete
    fun deleteMusic(music:Music)
}