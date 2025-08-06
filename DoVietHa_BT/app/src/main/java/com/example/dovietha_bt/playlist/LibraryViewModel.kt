package com.example.dovietha_bt.playlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.db.converter.toMusic
import com.example.dovietha_bt.db.converter.toMusicVM
import com.example.dovietha_bt.db.converter.toPlaylistVM
import com.example.dovietha_bt.db.repository.impl.MusicPlaylistRepositoryImpl
import com.example.dovietha_bt.db.repository.impl.MusicRepositoryImpl
import com.example.dovietha_bt.db.repository.impl.PlaylistRepositoryImpl
import com.example.dovietha_bt.getAllMp3Files
import com.example.dovietha_bt.myplaylist.model.MusicVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LibraryViewModel(application: Application) : AndroidViewModel(application) {
    private var _state = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()
    private var _event = MutableSharedFlow<LibraryEvent>()
    val event = _event.asSharedFlow()
    val musicPlaylistRepository = MusicPlaylistRepositoryImpl(application)
    val playlistRepository = PlaylistRepositoryImpl(application)
    val musicRepository = MusicRepositoryImpl(application)


    suspend fun getAllMusics(playlistId: Long): List<MusicVM> = withContext(Dispatchers.IO) {
        musicPlaylistRepository.getAllSongFromPlaylist(playlistId)
            .map {
                musicRepository.getMusicsById(it).toMusicVM()
            }
    }

    fun processIntent(intent: LibraryIntent) {
        when (intent) {
            is LibraryIntent.LoadPlaylists -> {
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

            is LibraryIntent.AddToPlaylist -> {
                CoroutineScope(Dispatchers.IO).launch {
                    musicPlaylistRepository.addSongToPlaylist(intent.music.id, intent.playlistId)

                    _state.value = _state.value.copy()
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
                viewModelScope.launch{
                    _state.value = _state.value.copy(
                        musics = getAllMp3Files(getApplication())
                    )
                }
            }
        }
    }
}