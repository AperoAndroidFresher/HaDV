package com.example.dovietha_bt.playlist

import com.example.dovietha_bt.myplaylist.model.Music

data class LibraryState(
    val musics : List<Music> = emptyList(),
    val isRemote : Boolean = false,
    val isShowDialog: Boolean = false
)

sealed interface LibraryIntent{
    object LoadSong: LibraryIntent
    data class AddToPlaylist(val music: Music): LibraryIntent
    object ShowLocal: LibraryIntent
    object ShowRemote: LibraryIntent
}

sealed interface LibraryEvent{
    object ShowDialog: LibraryEvent
}