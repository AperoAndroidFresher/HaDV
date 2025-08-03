package com.example.dovietha_bt.myplaylist.model

import android.graphics.Bitmap
import com.example.dovietha_bt.Screen
import java.util.UUID

data class Music(
    val image: ByteArray? = null,
    val name: String = "",
    val author: String = "",
    val duration: String =""
)

data class Playlist(
    val id: Long = 0,
    val name:String = "",
    val musics: List<Music> = emptyList(),
)

data class MyPlaylistState(
    val playlists: List<Playlist> = emptyList(),
    val musics: List<Music> = emptyList(),
    val isViewChange: Boolean = false,
    val showOption: Boolean = false,
    val playlistName:String =""
)

sealed interface MyPlaylistIntent{
    data class AddPlaylist(val playlist: Playlist): MyPlaylistIntent
    data class RemovePlaylist(val playlist: Playlist) : MyPlaylistIntent
    data class RenamePlaylist(val playlist: Playlist, val name: String): MyPlaylistIntent
    object ToggleView : MyPlaylistIntent
    data class RemoveSong(val item: Music) : MyPlaylistIntent
    data object LoadSong : MyPlaylistIntent
    data object ShowOption : MyPlaylistIntent
    data object HideOption : MyPlaylistIntent
}