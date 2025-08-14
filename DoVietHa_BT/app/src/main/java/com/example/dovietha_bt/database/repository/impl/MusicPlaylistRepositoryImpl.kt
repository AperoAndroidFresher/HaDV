package com.example.dovietha_bt.database.repository.impl

import android.content.Context
import android.util.Log
import com.example.dovietha_bt.database.AppDB
import com.example.dovietha_bt.database.entity.MusicPlaylistCrossRef
import com.example.dovietha_bt.database.repository.MusicPlaylistRepository

class MusicPlaylistRepositoryImpl(context: Context) : MusicPlaylistRepository {
    val musicPlaylistDao = AppDB.Companion.getInstance(context).MusicPlaylistCrossRefDao()
    override suspend fun addSongToPlaylist(musicId: Long, playlistId: Long) {
        musicPlaylistDao.addSongToPlaylist(
            MusicPlaylistCrossRef(
                playlistId = playlistId,
                musicId = musicId
            )
        )
    }

    override suspend fun deleteSongInPlaylist(musicId: Long, playlistId: Long) {
        val deleteRows = musicPlaylistDao.deleteSongInPlaylist(MusicPlaylistCrossRef(playlistId = playlistId, musicId = musicId))
        Log.d("DELETE TRIGGER","${playlistId}, ${musicId}")
    }

    override suspend fun getAllSongFromPlaylist(playlistId: Long): List<Long> {
        return musicPlaylistDao.getAllSongInPlaylist(playlistId)
    }
}
