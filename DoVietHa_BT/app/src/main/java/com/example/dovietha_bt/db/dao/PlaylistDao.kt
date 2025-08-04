package com.example.dovietha_bt.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dovietha_bt.db.entity.PlaylistDB
import com.example.dovietha_bt.myplaylist.model.Music
import com.example.dovietha_bt.myplaylist.model.Playlist
@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlist")
    fun getAllPlaylist():List<Playlist>
    @Insert
    fun addPlaylist(vararg playlist: Playlist)
    @Delete
    fun removePlaylist(playlist: Playlist)

}