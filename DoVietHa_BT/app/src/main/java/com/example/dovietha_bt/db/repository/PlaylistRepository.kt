package com.example.dovietha_bt.db.repository

import com.example.dovietha_bt.db.dao.PlaylistDao
import com.example.dovietha_bt.db.entity.Playlist


interface PlaylistRepository{
    suspend fun getAllPlaylist():List<Playlist>
    suspend fun addPlaylist(username:String,name:String)
    suspend fun removePlaylist(id: Long)
    suspend fun renamePlaylist(id: Long, newName:String)
}