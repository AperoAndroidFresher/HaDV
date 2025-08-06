package com.example.dovietha_bt.ui.main.myplaylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.db.converter.toMusicVM
import com.example.dovietha_bt.db.converter.toPlaylistVM
import com.example.dovietha_bt.db.repository.impl.MusicPlaylistRepositoryImpl
import com.example.dovietha_bt.db.repository.impl.MusicRepositoryImpl
import com.example.dovietha_bt.db.repository.impl.PlaylistRepositoryImpl
import com.example.dovietha_bt.ui.main.myplaylist.model.MusicVM
import com.example.dovietha_bt.ui.main.myplaylist.model.MyPlaylistIntent
import com.example.dovietha_bt.ui.main.myplaylist.model.MyPlaylistState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyPlaylistViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(MyPlaylistState())
    var state = _state.asStateFlow()
    val playlistRepository = PlaylistRepositoryImpl(application)
    val musicPlaylistRepository = MusicPlaylistRepositoryImpl(application)
    val musicRepository = MusicRepositoryImpl(application)

    suspend fun getAllMusics(playlistId: Long): List<MusicVM> = withContext(Dispatchers.IO) {
        musicPlaylistRepository.getAllSongFromPlaylist(playlistId)
            .map {
                musicRepository.getMusicsById(it).toMusicVM()
            }
    }

    fun processIntent(intent: MyPlaylistIntent) {
        when (intent) {
            is MyPlaylistIntent.RemoveSong -> {
                viewModelScope.launch {
                    musicPlaylistRepository.deleteSongInPlaylist(intent.playlistId, intent.musicId)
                }
            }

            MyPlaylistIntent.ToggleView -> {
                _state.value = _state.value.copy(isViewChange = !_state.value.isViewChange)
            }

            MyPlaylistIntent.ShowOption -> {
                _state.value = _state.value.copy(
                    showOption = true
                )
            }

            MyPlaylistIntent.HideOption -> {
                _state.value = _state.value.copy(
                    showOption = false
                )
            }

            is MyPlaylistIntent.AddPlaylist -> {
                viewModelScope.launch {
                    playlistRepository.addPlaylist(intent.username, intent.playlistName)
                }
            }

            is MyPlaylistIntent.RemovePlaylist -> {
                viewModelScope.launch {
                    playlistRepository.removePlaylist(intent.playlistId)
                }
            }

            is MyPlaylistIntent.RenamePlaylist -> {
                viewModelScope.launch {
                    playlistRepository.renamePlaylist(intent.playlistId, intent.newPlaylistName)
                }
            }

            MyPlaylistIntent.LoadPlaylists -> {
                viewModelScope.launch {
                    playlistRepository.getAllPlaylist()
                        .map { list ->
                            list.map {
                                val listMusic = getAllMusics(it.playlistId)
                                it.toPlaylistVM(listMusic)
                            }
                        }
                        .collect { playlistVMList ->
                            _state.update { it.copy(playlists = playlistVMList) }
                        }
                }
            }
        }
    }
}