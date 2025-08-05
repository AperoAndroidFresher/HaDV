package com.example.dovietha_bt.db.repository.impl

import android.content.Context
import com.example.dovietha_bt.db.AppDB
import com.example.dovietha_bt.db.entity.Playlist
import com.example.dovietha_bt.db.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow


class PlaylistRepositoryImpl(context: Context): PlaylistRepository {
    val playlistDao = AppDB.getInstance(context).PlaylistDao()
    override suspend fun getAllPlaylist(): Flow<List<Playlist>> {
        return playlistDao.getAllPlaylist()
    }

    override suspend fun addPlaylist(username:String,name:String) {
        playlistDao.addPlaylist(Playlist(username = username, name = name))
    }

    override suspend fun removePlaylist(id: Long) {
        val removedPlaylist = playlistDao.findPlaylistById(id)
        playlistDao.removePlaylist(removedPlaylist)
    }

    override suspend fun renamePlaylist(id:Long, newName:String) {
        val renamedPlaylist = playlistDao.findPlaylistById(id)
        playlistDao.renamePlaylist(renamedPlaylist.copy(name = newName))
    }
}