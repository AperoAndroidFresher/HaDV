package com.example.dovietha_bt.myplaylist.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object PlaylistRepository {
    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists = _playlists.asStateFlow()

    fun addPlaylist(playlist: Playlist) {
        _playlists.value = _playlists.value + playlist
    }

    fun addMusicToPlaylist(music: Music, playlistId: Long) {
        _playlists.value = _playlists.value.map { playlist ->
            if (playlist.id == playlistId) {
                playlist.copy(musics = playlist.musics + music)
            } else playlist
        }
    }
    fun removePlaylist(playlistId: Long) {
        _playlists.value = _playlists.value.filterNot { it.id == playlistId }
    }

    fun removeMusicFromPlaylist(music: Music, playlistId: Long) {
        _playlists.value = _playlists.value.map { playlist ->
            if (playlist.id == playlistId) {
                playlist.copy(musics = playlist.musics.filterNot { it == music })
            } else playlist
        }
    }
    fun renamePlaylist(playlistId: Long, newName: String) {
        _playlists.value = _playlists.value.map { playlist ->
            if (playlist.id == playlistId) playlist.copy(name = newName)
            else playlist
        }
    }
}
