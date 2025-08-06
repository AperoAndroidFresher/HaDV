package com.example.dovietha_bt.database.repository

import com.example.dovietha_bt.database.entity.Playlist
import kotlinx.coroutines.flow.Flow


interface PlaylistRepository{
    suspend fun getAllPlaylist(): Flow<List<Playlist>>
    suspend fun addPlaylist(username:String,name:String)
    suspend fun removePlaylist(id: Long)
    suspend fun renamePlaylist(id: Long, newName:String)
}