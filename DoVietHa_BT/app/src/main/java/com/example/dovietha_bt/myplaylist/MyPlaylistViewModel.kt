package com.example.dovietha_bt.myplaylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.db.converter.toPlaylistVM
import com.example.dovietha_bt.db.repository.impl.MusicPlaylistRepositoryImpl
import com.example.dovietha_bt.db.repository.impl.PlaylistRepositoryImpl
import com.example.dovietha_bt.myplaylist.model.MyPlaylistIntent
import com.example.dovietha_bt.myplaylist.model.MyPlaylistState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyPlaylistViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(MyPlaylistState())
    var state = _state.asStateFlow()
    val playlistDao = PlaylistRepositoryImpl(application)
    val musicPlaylistDao = MusicPlaylistRepositoryImpl(application)
    fun processIntent(intent: MyPlaylistIntent) {
        when (intent) {
            is MyPlaylistIntent.RemoveSong -> {
                viewModelScope.launch {
                    //MyPlaylistRepository.removeMusicFromPlaylist(intent.item, intent.playlistId)
                    musicPlaylistDao.deleteSongInPlaylist(intent.playlistId, intent.musicId)
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
                    playlistDao.addPlaylist(intent.username, intent.playlistName)
                }
                //MyPlaylistRepository.addPlaylist(intent.playlist)
            }

            is MyPlaylistIntent.RemovePlaylist -> {
                viewModelScope.launch {
                    playlistDao.removePlaylist(intent.playlistId)
                    //MyPlaylistRepository.removePlaylist(intent.playlist.id)
                }
            }

            is MyPlaylistIntent.RenamePlaylist -> {
                viewModelScope.launch {
                    playlistDao.renamePlaylist(intent.playlistId, intent.newPlaylistName)
                }
            }

            MyPlaylistIntent.LoadPlaylist -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(
                        playlists = playlistDao.getAllPlaylist().map { it.toPlaylistVM() })
                }
            }
        }
    }
}