package com.example.dovietha_bt.ui.main.library

import com.example.dovietha_bt.ui.main.myplaylist.model.MusicVM
import com.example.dovietha_bt.ui.main.myplaylist.model.PlaylistVM

data class LibraryState(
    val musics: List<MusicVM> = emptyList(),
    val isLocal: Boolean = true,
    val isShowDialog: Boolean = false,
    val playlist: PlaylistVM = PlaylistVM(),
    val playlists: List<PlaylistVM> = emptyList(),
    val isLoading: Boolean = false,
    val isDisconnect:Boolean = false
)

sealed interface LibraryIntent {
    object LoadSong : LibraryIntent
    object LoadPlaylists : LibraryIntent
    data class AddToPlaylist(val music: MusicVM, val playlistId: Long) : LibraryIntent
    object ShowLocal : LibraryIntent
    object ShowRemote : LibraryIntent
}

sealed interface LibraryEvent {
    object ShowDialog : LibraryEvent
}