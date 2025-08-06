package com.example.dovietha_bt.db.repository.impl

import android.content.Context
import com.example.dovietha_bt.db.AppDB
import com.example.dovietha_bt.db.entity.MusicPlaylistCrossRef
import com.example.dovietha_bt.db.repository.MusicPlaylistRepository
import kotlinx.coroutines.flow.Flow

class MusicPlaylistRepositoryImpl(context: Context): MusicPlaylistRepository {
    val musicPlaylistDao = AppDB.Companion.getInstance(context).MusicPlaylistCrossRefDao()
    override suspend fun addSongToPlaylist(musicId:Long, playlistId:Long) {
        musicPlaylistDao.addSongToPlaylist(MusicPlaylistCrossRef(playlistId = playlistId, musicId = musicId))
    }
    override suspend fun deleteSongInPlaylist(musicId:Long, playlistId:Long) {
        musicPlaylistDao.deleteSongInPlaylist(MusicPlaylistCrossRef(playlistId,musicId))
    }

    override suspend fun getAllSongFromPlaylist(playlistId: Long): List<Long> {
        return musicPlaylistDao.getAllSongInPlaylist(playlistId)
    }
}