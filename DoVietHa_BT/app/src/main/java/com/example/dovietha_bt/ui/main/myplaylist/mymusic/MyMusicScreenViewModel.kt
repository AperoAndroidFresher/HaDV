package com.example.dovietha_bt.ui.main.myplaylist.mymusic

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.database.converter.toMusicVM
import com.example.dovietha_bt.database.converter.toPlaylistVM
import com.example.dovietha_bt.database.repository.impl.MusicPlaylistRepositoryImpl
import com.example.dovietha_bt.database.repository.impl.MusicRepositoryImpl
import com.example.dovietha_bt.database.repository.impl.PlaylistRepositoryImpl
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.map

class MyMusicScreenViewModel(application: Application): AndroidViewModel(application) {
    val playlistRepository = PlaylistRepositoryImpl(application)
    val musicPlaylistRepository = MusicPlaylistRepositoryImpl(application)
    val musicRepository = MusicRepositoryImpl(application)
    private var _state = MutableStateFlow(MyMusicState())
    val state = _state.asStateFlow()
    fun processIntent(intent: MyMusicIntent){
        when(intent){
            is MyMusicIntent.CurrentSong -> {
                _state.update { it.copy(
                    currentSong = intent.music
                ) }
                Log.d("CURRENT SONG", "${_state.value.currentSong}")
            }

            MyMusicIntent.LoadPlaylists -> {
//                viewModelScope.launch {
//                    playlistRepository.getAllPlaylist()
//                        .map { list ->
//                            list.map {
//                                val listMusic = getAllMusics(it.playlistId)
//                                Log.d("PLAYLIST","$listMusic")
//                                it.toPlaylistVM(listMusic)
//
//                            }
//                        }
//                        .collect { playlistVMList ->
//                            _state.update { it.copy(playlists = playlistVMList) }
//                        }
//
//                }
            }
            is MyMusicIntent.RemoveSong -> TODO()
        }
    }
    suspend fun getAllMusics(playlistId: Long): List<MusicVM> = withContext(Dispatchers.IO) {
        musicPlaylistRepository.getAllSongFromPlaylist(playlistId)
            .map {
                Log.d("MUSIC","${musicRepository.getMusicsById(it).toMusicVM()}")
                musicRepository.getMusicsById(it).toMusicVM()
            }
    }
}
