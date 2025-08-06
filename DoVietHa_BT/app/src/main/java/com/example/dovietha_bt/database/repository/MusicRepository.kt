package com.example.dovietha_bt.database.repository

import com.example.dovietha_bt.database.entity.Music
import kotlinx.coroutines.flow.Flow


interface MusicRepository {
    suspend fun getAllMusics(): Flow<List<Music>>
    suspend fun getMusicsById(id:Long): Music
    suspend fun insertMusic(music: Music): Long
}