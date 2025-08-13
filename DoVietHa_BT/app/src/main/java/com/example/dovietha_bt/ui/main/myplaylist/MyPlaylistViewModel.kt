package com.example.dovietha_bt.ui.main.myplaylist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.MusicServiceConnectionHelper
import com.example.dovietha_bt.database.converter.toMusicVM
import com.example.dovietha_bt.database.converter.toPlaylistVM
import com.example.dovietha_bt.database.repository.impl.MusicPlaylistRepositoryImpl
import com.example.dovietha_bt.database.repository.impl.MusicRepositoryImpl
import com.example.dovietha_bt.database.repository.impl.PlaylistRepositoryImpl
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
                Log.d("MUSIC","${musicRepository.getMusicsById(it).toMusicVM()}")
                musicRepository.getMusicsById(it).toMusicVM()
            }
    }

    fun processIntent(intent: MyPlaylistIntent) {
        when (intent) {
            is MyPlaylistIntent.RemoveSong -> {
                viewModelScope.launch {
                    musicPlaylistRepository.deleteSongInPlaylist(playlistId =intent.playlistId, musicId = intent.musicId)
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

            is MyPlaylistIntent.LoadPlaylists -> {
                viewModelScope.launch {
                    playlistRepository.getAllPlaylist(intent.username)
                        .map { list ->
                            list.map {
                                val listMusic = getAllMusics(it.playlistId)
                                Log.d("PLAYLIST","$listMusic")
                                it.toPlaylistVM(listMusic)
                            }
                        }
                        .collect { playlistVMList ->
                            _state.update { it.copy(playlists = playlistVMList) }
                        }
                    
                }
            }

            is MyPlaylistIntent.CurrentSong -> {
                _state.update { it.copy(
                    currentSong = intent.music
                ) }
                Log.d("CURRENT SONG","${_state.value.currentSong}")
            }

            MyPlaylistIntent.IsPlaying -> {
                viewModelScope.launch(Dispatchers.IO){
                    _state.update {
                        it.copy(
                            isPlaying = MusicServiceConnectionHelper.musicService?.isPlaying() ?: false,
                        )
                    }
                }
            }

            MyPlaylistIntent.ToggleShuffle -> {
                MusicServiceConnectionHelper.musicService?.toggleShuffle()
                val current = _state.value.isShuffleOn
                _state.update { it.copy(isShuffleOn = !current) }
            }

            MyPlaylistIntent.ToggleRepeat -> {
                MusicServiceConnectionHelper.musicService?.toggleRepeat()
                val current = _state.value.isRepeatOn
                _state.update { it.copy(isRepeatOn = !current) }
            }

            //Error
            is MyPlaylistIntent.MoveSong -> {
                _state.update { state -> 
                    val updateSong = state.playlists[intent.currentIndex].musics.toMutableList()
                    val song = updateSong.removeAt(intent.from)
                    updateSong.add(intent.to, song)
                    state.copy(playlists = state.playlists.apply { updateSong })
                }
            }
        }
    }
}
