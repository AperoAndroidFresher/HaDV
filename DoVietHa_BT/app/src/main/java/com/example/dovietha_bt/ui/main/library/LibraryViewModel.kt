package com.example.dovietha_bt.ui.main.library

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.api.music.ApiMusicClient
import com.example.dovietha_bt.api.music.ApiMusic
import com.example.dovietha_bt.common.getAllMp3Files
import com.example.dovietha_bt.common.toMusicVM
import com.example.dovietha_bt.database.converter.toMusicVM
import com.example.dovietha_bt.database.converter.toPlaylistVM
import com.example.dovietha_bt.database.repository.impl.MusicPlaylistRepositoryImpl
import com.example.dovietha_bt.database.repository.impl.MusicRepositoryImpl
import com.example.dovietha_bt.database.repository.impl.PlaylistRepositoryImpl
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

            }

            LibraryIntent.ShowRemote -> {
                getListMusic()
            }

            LibraryIntent.LoadSong -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(
                        musics = getAllMp3Files(getApplication()),
                        isLocal = true,
                        isDisconnect = false
                    )
                }
            }
        }
    }

    fun getListMusic(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isLoading = true, isLocal = false
                )
            }
            delay(3000L)
            val call = ApiMusicClient.build().getMusics()
            call.enqueue(object : Callback<List<ApiMusic>>{
                override fun onResponse(
                    call: Call<List<ApiMusic>?>,
                    response: Response<List<ApiMusic>?>
                ) {
                    when{
                        response.isSuccessful->{
                            val api =response.body()?.map{it.toMusicVM()}?: emptyList()
                            _state.update {
                                it.copy(musics = api , isLoading = false , isDisconnect = false)
                            }
                        }
                    }
                }

                override fun onFailure(
                    call: Call<List<ApiMusic>?>,
                    t: Throwable
                ) {
                    _state.update { it.copy(isLoading = false, isDisconnect = true) }
                    Log.v(TAG,"onFailure: ${t.message}")
                }

            })
        }
    }
}