package com.example.dovietha_bt.db.repository

import com.example.dovietha_bt.db.dao.PlaylistDao
import com.example.dovietha_bt.myplaylist.model.Playlist

class PlaylistRepository(val dao: PlaylistDao) {
    fun getAllPlaylist():List<Playlist>{
        return dao.getAllPlaylist()
    }
}