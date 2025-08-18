package com.example.dovietha_bt.ui.main.library

import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import com.example.dovietha_bt.ui.main.myplaylist.PlaylistVM

data class LibraryState(
    val musics: List<MusicVM> = emptyList(),
    val isLocal: Boolean = true,
    val isShowDialog: Boolean = false,
    val playlist: PlaylistVM = PlaylistVM(),
    val playlists: List<PlaylistVM> = emptyList(),
    val isLoading: Boolean = false,
    val canLoadMusic: Boolean = false
)

sealed interface LibraryIntent {
    object LoadLocalSong : LibraryIntent
    data class LoadPlaylists(val username: String) : LibraryIntent
    data class AddToPlaylist(val music: MusicVM, val playlistId: Long) : LibraryIntent
    object LoadRemoteSong : LibraryIntent
}

sealed interface LibraryEvent {
    object ShowDialog : LibraryEvent
}
