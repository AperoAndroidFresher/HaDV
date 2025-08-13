package com.example.dovietha_bt.ui.main.myplaylist.mymusic

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.database.converter.toMusicVM
import com.example.dovietha_bt.database.repository.impl.MusicPlaylistRepositoryImpl
import com.example.dovietha_bt.database.repository.impl.MusicRepositoryImpl
import com.example.dovietha_bt.database.repository.impl.PlaylistRepositoryImpl
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyMusicScreenViewModel(application: Application) : AndroidViewModel(application) {
    val musicPlaylistRepository = MusicPlaylistRepositoryImpl(application)
    val musicRepository = MusicRepositoryImpl(application)
    private var _state = MutableStateFlow(MyMusicState())
    val state = _state.asStateFlow()
    fun processIntent(intent: MyMusicIntent) {
        when (intent) {
            is MyMusicIntent.CurrentSong -> {
                _state.update {
                    it.copy(
                        currentSong = intent.music,
                    )
                }
                Log.d("CURRENT SONG", "${_state.value.currentSong}")
            }

            is MyMusicIntent.LoadSongs -> {
                viewModelScope.launch {
                    val list = getAllMusics(intent.playlistId)
                    _state.update {
                        it.copy(
                            listSong = list.toList(),
                        )
                    }
                }
            }

            is MyMusicIntent.RemoveSong -> {
                viewModelScope.launch {
                    musicPlaylistRepository.deleteSongInPlaylist(playlistId =intent.playlistId, musicId = intent.musicId)
                    val updatedList = getAllMusics(intent.playlistId)
                    _state.update {
                        it.copy(
                            listSong = updatedList.toList()
                        )
                    }
                }
            }

            is MyMusicIntent.MoveSong -> {
                _state.update { state ->
                    val updatedSongs = state.listSong.toMutableList()
                    val song = updatedSongs.removeAt(intent.from)
                    updatedSongs.add(intent.to, song)
                    state.copy(listSong = updatedSongs)
                }
            }
        }
    }

    suspend fun getAllMusics(playlistId: Long): List<MusicVM> = withContext(Dispatchers.IO) {
        musicPlaylistRepository.getAllSongFromPlaylist(playlistId)
            .map {
                Log.d("MUSIC", "${musicRepository.getMusicsById(it).toMusicVM()}")
                musicRepository.getMusicsById(it).toMusicVM()
            }
    }
}
