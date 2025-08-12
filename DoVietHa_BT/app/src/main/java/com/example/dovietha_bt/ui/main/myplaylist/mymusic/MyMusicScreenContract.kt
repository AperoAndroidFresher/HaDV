package com.example.dovietha_bt.ui.main.myplaylist.mymusic

import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistIntent

data class MyMusicState(
    val currentSong: MusicVM = MusicVM()
)

sealed interface MyMusicIntent{
    object LoadPlaylists : MyMusicIntent
    data class CurrentSong(val music: MusicVM): MyMusicIntent
    data class RemoveSong(val musicId: Long, val playlistId: Long) : MyMusicIntent
}
