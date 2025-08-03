package com.example.dovietha_bt.playlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.getAllMp3Files
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(application: Application) : AndroidViewModel(application) {
    private var _state = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()
    private var _event = MutableSharedFlow<LibraryEvent>()
    val event = _event.asSharedFlow()
    fun processIntent(intent: LibraryIntent) {
        when (intent) {
            is LibraryIntent.AddToPlaylist -> {
                val newPlaylist =intent.playlist
                newPlaylist.musics.toMutableList().add(intent.music)
                _state.value = _state.value.copy(
                    playlist = newPlaylist
                )
//                val newList = _state.value.musics.toMutableList()
//                newList.add(intent.music)
//                _state.value = _state.value.copy(
//                    musics = newList
//                )
                viewModelScope.launch {
                    _event.emit(LibraryEvent.ShowDialog)
                }
            }

            LibraryIntent.ShowLocal -> {
                _state.update {
                    it.copy(
                        isRemote = false
                    )
                }
            }

            LibraryIntent.ShowRemote -> {
                _state.update {
                    it.copy(
                        isRemote = true
                    )
                }
            }

            LibraryIntent.LoadSong -> {
                _state.value = _state.value.copy(
                    musics = getAllMp3Files(getApplication())
                )
            }
        }
    }
}