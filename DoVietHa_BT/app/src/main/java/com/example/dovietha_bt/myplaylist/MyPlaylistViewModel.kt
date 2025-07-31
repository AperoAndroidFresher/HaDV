package com.example.dovietha_bt.myplaylist

import androidx.lifecycle.ViewModel
import com.example.dovietha_bt.getAllMp3Files
import com.example.dovietha_bt.myplaylist.model.Music
import com.example.dovietha_bt.myplaylist.model.MyPlaylistIntent
import com.example.dovietha_bt.myplaylist.model.MyPlaylistState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MyPlaylistViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(MyPlaylistState())
    var state = _state.asStateFlow()
    fun processIntent(intent: MyPlaylistIntent) {
        when (intent) {
            is MyPlaylistIntent.RemoveSong -> {
                val list = _state.value.musics.toMutableList()
                list.remove(intent.item)
                _state.value = _state.value.copy(musics = list)
            }
            MyPlaylistIntent.ToggleView -> {
                _state.value = _state.value.copy(isViewChange = !_state.value.isViewChange)
            }

            MyPlaylistIntent.LoadSong ->{
                _state.value = _state.value.copy(
                    musics = getAllMp3Files(getApplication())
                )
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
        }
    }
}