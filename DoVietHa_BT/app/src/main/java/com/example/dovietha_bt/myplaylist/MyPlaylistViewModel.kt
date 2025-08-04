package com.example.dovietha_bt.myplaylist

import androidx.lifecycle.ViewModel
import com.example.dovietha_bt.getAllMp3Files
import com.example.dovietha_bt.myplaylist.model.Music
import com.example.dovietha_bt.myplaylist.model.MyPlaylistIntent
import com.example.dovietha_bt.myplaylist.model.MyPlaylistState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.dovietha_bt.myplaylist.model.PlaylistRepository
import kotlinx.coroutines.flow.update

class MyPlaylistViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(MyPlaylistState())
    var state = _state.asStateFlow()
    fun processIntent(intent: MyPlaylistIntent) {
        when (intent) {
            is MyPlaylistIntent.RemoveSong -> PlaylistRepository.removeMusicFromPlaylist(intent.item,intent.playlistId)
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

            is MyPlaylistIntent.AddPlaylist -> PlaylistRepository.addPlaylist(intent.playlist)
            is MyPlaylistIntent.RemovePlaylist -> PlaylistRepository.removePlaylist(intent.playlist.id)
            is MyPlaylistIntent.RenamePlaylist -> PlaylistRepository.renamePlaylist(intent.playlist.id, intent.name)

        }
    }
}