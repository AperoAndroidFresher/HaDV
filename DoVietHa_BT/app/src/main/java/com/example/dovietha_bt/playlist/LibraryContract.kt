package com.example.dovietha_bt.playlist

import com.example.dovietha_bt.myplaylist.model.MusicVM
import com.example.dovietha_bt.myplaylist.model.PlaylistVM

data class LibraryState(
    val musics : List<MusicVM> = emptyList(),
    val isRemote : Boolean = false,
    val isShowDialog: Boolean = false,
    val playlist: PlaylistVM = PlaylistVM(),
    val playlists: List<PlaylistVM> = emptyList())

sealed interface LibraryIntent{
    object LoadSong: LibraryIntent
    object LoadPlaylists: LibraryIntent
    data class AddToPlaylist(val music: MusicVM, val playlistId: Long): LibraryIntent
    object ShowLocal: LibraryIntent
    object ShowRemote: LibraryIntent
}

sealed interface LibraryEvent{
    object ShowDialog: LibraryEvent
}