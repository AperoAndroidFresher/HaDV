package com.example.dovietha_bt.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dovietha_bt.database.entity.Playlist
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlist")
    fun getAllPlaylist(): Flow<List<Playlist>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlaylist(playlist: Playlist)

    @Delete
    suspend fun removePlaylist(playlist: Playlist)

    @Update
    suspend fun renamePlaylist(playlist: Playlist)

    @Query("SELECT * FROM playlist WHERE name = :playlistName AND username =:username")
    suspend fun findPlaylistByNameAndUser(playlistName: String, username: String): Playlist

    @Query("SELECT * FROM playlist WHERE playlistId = :id")
    suspend fun findPlaylistById(id: Long): Playlist
}