package com.example.dovietha_bt.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dovietha_bt.db.entity.Playlist
@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlist")
    suspend fun getAllPlaylist():List<Playlist>
    @Insert
    suspend fun addPlaylist(playlist: Playlist)
    @Delete
    suspend fun removePlaylist(playlist: Playlist)
    @Update
    suspend fun renamePlaylist(playlist: Playlist)
    @Query("SELECT * FROM playlist WHERE name = :playlistName AND username =:username")
    suspend fun findPlaylistByNameAndUser(playlistName:String,username:String): Playlist
    @Query("SELECT * FROM playlist WHERE playlistId = :id")
    suspend fun findPlaylistById(id: Long): Playlist
}