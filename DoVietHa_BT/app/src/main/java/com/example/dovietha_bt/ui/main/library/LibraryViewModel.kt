package com.example.dovietha_bt.ui.main.library

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.api.music.ApiMusic
import com.example.dovietha_bt.api.music.ApiMusicClient
import com.example.dovietha_bt.common.getAllMp3Files
import com.example.dovietha_bt.common.getEmbeddedImageBytes
import com.example.dovietha_bt.common.toMusicVM
import com.example.dovietha_bt.database.converter.toMusic
import com.example.dovietha_bt.database.converter.toMusicVM
import com.example.dovietha_bt.database.converter.toPlaylistVM
import com.example.dovietha_bt.database.repository.impl.MusicPlaylistRepositoryImpl
import com.example.dovietha_bt.database.repository.impl.MusicRepositoryImpl
import com.example.dovietha_bt.database.repository.impl.PlaylistRepositoryImpl
import com.example.dovietha_bt.common.downloadMusicWithEnqueue
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import kotlin.math.absoluteValue

class LibraryViewModel(application: Application) : AndroidViewModel(application) {
    val appContext = application.applicationContext
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
//                    Log.d("LIBRARY DEBUG","${intent.music}")
                    musicRepository.insertMusic(intent.music.toMusic())
                    musicPlaylistRepository.addSongToPlaylist(intent.music.id, intent.playlistId)
                    
                }
            }

            LibraryIntent.LoadRemoteSong -> {
                getListMusic()
            }

            LibraryIntent.LoadLocalSong -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(
                        musics = getAllMp3Files(getApplication()),
                        isLocal = true,
                        canLoadMusic = false,
                    )
                    _state.value.musics.forEach {
                        musicRepository.insertMusic(it.toMusic())
                    }
                }
            }
        }
    }

    fun getListMusic() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, isLocal = false) }

            fetchApiMusicList(
                onSuccess = { apiList ->
                    downloadAllIfNeeded(apiList)
                    val musicVMList = convertToMusicVM(apiList)
                    Log.d("LIBRARY DEBUG","${musicVMList}")
                    _state.update {
                        it.copy(
                            musics = musicVMList,
                            isLoading = false,
                            canLoadMusic = false,
                        )
                    }
                    
                },
                onError = {
                    val musics = getDownloadedMusicListFromFileNames()
                    if (musics.isEmpty()) {
                        _state.update { it.copy(isLoading = false, canLoadMusic = true) }
                    } else {
                        _state.update { it.copy(musics = musics, isLoading = false, canLoadMusic = false) }
                    }
                },
            )
        }
    }

    private fun fetchApiMusicList(
        onSuccess: (List<ApiMusic>) -> Unit,
        onError: () -> Unit,
    ) {
        val call = ApiMusicClient.build().getMusics()
        call.enqueue(
            object : Callback<List<ApiMusic>> {
                override fun onResponse(
                    call: Call<List<ApiMusic>>,
                    response: Response<List<ApiMusic>>,
                ) {
                    if (response.isSuccessful) {
                        val list = response.body() ?: emptyList()
                        onSuccess(list)
                    } else {
                        onError()
                    }
                }

                override fun onFailure(call: Call<List<ApiMusic>>, t: Throwable) {
                    Log.e("API", "Lỗi API: ${t.message}")
                    onError()
                }
            },
        )
    }

    private fun downloadAllIfNeeded(apiMusicList: List<ApiMusic>) {
        for (music in apiMusicList) {
            val file = File(appContext.filesDir, "${music.title}.mp3")
            if (!file.exists()) {
                downloadMusicWithEnqueue(
                    context = appContext,
                    url = music.path,
                    fileName = "${music.title}_${music.artist}.mp3",
                ) { success, message ->
                    Log.d("Download", if (success) "Đã tải: ${music.title}" else "Lỗi: $message")
                }
            }
        }
    }

    private fun convertToMusicVM(apiList: List<ApiMusic>): List<MusicVM> {
        return apiList.map {
            val localFile = File(appContext.filesDir, "${it.title}.mp3")
            Log.d("LIBRARY DEBUG 1","${localFile.absolutePath}")
            it.toMusicVM(localPath = if (localFile.exists()) localFile.absolutePath else "").copy(id = "${it.title}_${it.artist}".hashCode().toLong().absoluteValue + 1, path = localFile.absolutePath)
        }
    }

    fun getDownloadedMusicListFromFileNames(): List<MusicVM> {
        val dir = appContext.filesDir
        val mp3Files = dir.listFiles { _, name -> name.endsWith(".mp3") } ?: return emptyList()

        return mp3Files.map { file ->
            val nameParts = file.nameWithoutExtension.split("_")
            val title = nameParts.getOrNull(0) ?: file.nameWithoutExtension
            val artist = nameParts.getOrNull(1) ?: "Unknown"
            val id = "${title}_${artist}".hashCode().toLong().absoluteValue + 1
            
            MusicVM(
                id = id,
                name = title,
                author = artist,
                path = file.absolutePath,
                image = getEmbeddedImageBytes(file.absolutePath),
            )
        }
    }
}
