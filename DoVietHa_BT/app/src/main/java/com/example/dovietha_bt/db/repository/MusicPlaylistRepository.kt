package com.example.dovietha_bt.db.repository

import com.example.dovietha_bt.db.entity.MusicPlaylistCrossRef

interface MusicPlaylistRepository {
    suspend fun addSongToPlaylist(musicId:Long, playlistId:Long)
    suspend fun deleteSongInPlaylist(musicId:Long, playlistId:Long)
    suspend fun getAllSongFromPlaylist(playlistId: Long): List<Long>
}