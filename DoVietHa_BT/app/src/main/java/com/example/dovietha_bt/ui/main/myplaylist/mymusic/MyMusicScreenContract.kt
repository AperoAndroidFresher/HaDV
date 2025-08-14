package com.example.dovietha_bt.ui.main.myplaylist.mymusic

import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistIntent

data class MyMusicState(
    val listSong: List<MusicVM> = emptyList(),
    val currentSong: MusicVM = MusicVM()
)

sealed interface MyMusicIntent{
    data class LoadSongs(val playlistId: Long) : MyMusicIntent
    data class CurrentSong(val music: MusicVM): MyMusicIntent
    data class RemoveSong(val musicId: Long, val playlistId: Long) : MyMusicIntent
    data class MoveSong(val from:Int,val to:Int):MyMusicIntent
}
