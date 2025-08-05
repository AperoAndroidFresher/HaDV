package com.example.dovietha_bt.playlist

import com.example.dovietha_bt.myplaylist.model.Music
import com.example.dovietha_bt.myplaylist.model.PlaylistVM

data class LibraryState(
    val musics : List<Music> = emptyList(),
    val isRemote : Boolean = false,
    val isShowDialog: Boolean = false,
    val playlist: PlaylistVM = PlaylistVM())

sealed interface LibraryIntent{
    object LoadSong: LibraryIntent
    data class AddToPlaylist(val music: Music, val playlist: PlaylistVM): LibraryIntent
    object ShowLocal: LibraryIntent
    object ShowRemote: LibraryIntent
}

sealed interface LibraryEvent{
    object ShowDialog: LibraryEvent
}