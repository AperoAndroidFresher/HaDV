package com.example.dovietha_bt.database.repository

interface MusicPlaylistRepository {
    suspend fun addSongToPlaylist(musicId: Long, playlistId: Long)
    suspend fun deleteSongInPlaylist(musicId: Long, playlistId: Long)
    suspend fun getAllSongFromPlaylist(playlistId: Long): List<Long>
}