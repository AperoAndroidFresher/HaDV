package com.example.dovietha_bt.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dovietha_bt.db.entity.MusicPlaylistCrossRef
import com.example.dovietha_bt.db.entity.Playlist
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicPlaylistCrossRefDao {
    @Query("SELECT musicId FROM musicplaylistcrossref WHERE playlistId = :playlistId")
    fun getAllSongInPlaylist(playlistId: Long): List<Long>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSongToPlaylist(crossRef: MusicPlaylistCrossRef)
    @Delete
    suspend fun deleteSongInPlaylist(crossRef: MusicPlaylistCrossRef)

//    @Query("""
//        DELETE FROM MusicPlaylistCrossRef WHERE playlistId = :playlistId
//    """)
//    fun deletePlaylist(playlistId: Long)
}