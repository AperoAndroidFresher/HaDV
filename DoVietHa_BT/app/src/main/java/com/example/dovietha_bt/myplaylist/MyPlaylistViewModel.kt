package com.example.dovietha_bt.myplaylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.db.converter.toMusicVM
import com.example.dovietha_bt.db.converter.toPlaylistVM
import com.example.dovietha_bt.db.repository.impl.MusicPlaylistRepositoryImpl
import com.example.dovietha_bt.db.repository.impl.MusicRepositoryImpl
import com.example.dovietha_bt.db.repository.impl.PlaylistRepositoryImpl
import com.example.dovietha_bt.myplaylist.model.MusicVM
import com.example.dovietha_bt.myplaylist.model.MyPlaylistIntent
import com.example.dovietha_bt.myplaylist.model.MyPlaylistState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MyPlaylistViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(MyPlaylistState())
    var state = _state.asStateFlow()
    val playlistRepository = PlaylistRepositoryImpl(application)
    val musicPlaylistRepository = MusicPlaylistRepositoryImpl(application)
    val musicRepository = MusicRepositoryImpl(application)

    init {
        viewModelScope.launch {
            playlistRepository.getAllPlaylist()
                .map { list ->
                    list.map {
                        val listMusic = getAllMusics(it.playlistId)
                        it.toPlaylistVM(listMusic)
                    }
                }
                .collect { playlistVMList ->
                    _state.value = _state.value.copy(playlists = playlistVMList)
                }
        }
    }

    suspend fun getAllMusics(playlistId: Long): List<MusicVM> {
        return musicPlaylistRepository.getAllSongFromPlaylist(playlistId)
            .map { musicIdList: List<Long> ->
                musicIdList.map { musicId ->
                    musicRepository.getMusicsById(musicId).toMusicVM()
                }
            }.first()
    }

    fun processIntent(intent: MyPlaylistIntent) {
        when (intent) {
            is MyPlaylistIntent.RemoveSong -> {
                viewModelScope.launch {
                    //MyPlaylistRepository.removeMusicFromPlaylist(intent.item, intent.playlistId)
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
                //MyPlaylistRepository.addPlaylist(intent.playlist)
            }

            is MyPlaylistIntent.RemovePlaylist -> {
                viewModelScope.launch {
                    playlistRepository.removePlaylist(intent.playlistId)
                    //MyPlaylistRepository.removePlaylist(intent.playlist.id)
                }
            }

            is MyPlaylistIntent.RenamePlaylist -> {
                viewModelScope.launch {
                    playlistRepository.renamePlaylist(intent.playlistId, intent.newPlaylistName)
                }
            }
        }
    }
}