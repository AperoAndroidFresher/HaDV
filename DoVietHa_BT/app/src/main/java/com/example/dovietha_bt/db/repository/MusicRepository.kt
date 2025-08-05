package com.example.dovietha_bt.db.repository

import com.example.dovietha_bt.db.entity.Music


interface MusicRepository {
    suspend fun getAllMusics():List<Music>
    suspend fun getMusicsById(id:Long): Music
}