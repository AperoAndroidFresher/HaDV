package com.example.dovietha_bt.myplaylist.model

import android.graphics.Bitmap
import java.util.UUID

data class Music(
    val image: ByteArray? = null,
    val name: String,
    val author: String,
    val duration: String
)

data class MyPlaylistState(
    val musics: List<Music> = emptyList(),
    val isViewChange: Boolean = false,
    val showOption: Boolean = false
)

sealed interface MyPlaylistIntent{
    object ToggleView : MyPlaylistIntent
    data class RemoveSong(val item: Music) : MyPlaylistIntent
    data object LoadSong : MyPlaylistIntent
    data object ShowOption : MyPlaylistIntent
    data object HideOption : MyPlaylistIntent
}