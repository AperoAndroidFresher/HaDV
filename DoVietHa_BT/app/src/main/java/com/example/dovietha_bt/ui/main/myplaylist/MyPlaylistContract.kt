package com.example.dovietha_bt.ui.main.myplaylist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicVM(
    val id: Long = 0,
    val name: String = "",
    val author: String = "",
    val duration: String = "",
    val path: String = ""
): Parcelable

data class PlaylistVM(
    val id: Long = 0,
    val name: String = "",
    val username: String = "",
    val musics: List<MusicVM> = emptyList(),
)

data class MyPlaylistState(
    val playlists: List<PlaylistVM> = emptyList(),
    val currentSong: MusicVM = MusicVM(),
    val isViewChange: Boolean = false,
    val showOption: Boolean = false,
    val playlistName: String = "",
    val isPlaying: Boolean = false,
    val isShuffleOn: Boolean = false,
    val isRepeatOn: Boolean = false,
)

sealed interface MyPlaylistIntent {
    data class AddPlaylist(val playlistName: String, val username: String) : MyPlaylistIntent
    data class RemovePlaylist(val playlistId: Long) : MyPlaylistIntent
    data class RenamePlaylist(val playlistId: Long, val newPlaylistName: String) : MyPlaylistIntent
    
    object ToggleView : MyPlaylistIntent
    data class LoadPlaylists(val username: String) : MyPlaylistIntent
    data class CurrentSong(val music: MusicVM): MyPlaylistIntent
    data class RemoveSong(val musicId: Long, val playlistId: Long) : MyPlaylistIntent
    data class MoveSong(val from:Int,val to:Int, val currentIndex: Int): MyPlaylistIntent
    
    data object IsPlaying : MyPlaylistIntent

    object ToggleShuffle : MyPlaylistIntent
    object ToggleRepeat : MyPlaylistIntent
    
    //thừa
    data object ShowOption : MyPlaylistIntent
    data object HideOption : MyPlaylistIntent
}
