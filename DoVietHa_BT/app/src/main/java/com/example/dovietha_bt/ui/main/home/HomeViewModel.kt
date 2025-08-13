package com.example.dovietha_bt.ui.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.dovietha_bt.api.homescreen.HomeScreenClient
import com.example.dovietha_bt.api.homescreen.topalbum.Album
import com.example.dovietha_bt.api.homescreen.topalbum.TopAlbumsResponse
import com.example.dovietha_bt.api.homescreen.topartist.Artist
import com.example.dovietha_bt.api.homescreen.topartist.TopArtistsResponse
import com.example.dovietha_bt.api.homescreen.toptracks.TopTracksResponse
import com.example.dovietha_bt.api.homescreen.toptracks.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel() {
    private var _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    
    fun processIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadTopAlbums ->{
                fetchApiTopAlbum(
                    onSuccess = { topAlbums ->
                        val topAlbumsList = topAlbums.map{response -> 
                            response.toTopAlbum()
                        }
                        _state.update { 
                            it.copy(topAlbums = topAlbumsList, canLoadData = true)
                        }
                        
                    },
                    onError = { 
                        _state.update {  
                            it.copy(canLoadData = false)
                        }
                    }
                )
            }
            HomeIntent.LoadTopArtists -> {
                fetchApiTopTrack(
                    onSuccess = { topTracks ->
                        val topTracksList = topTracks.map{ response ->
                            response.toTopTrack()
                        }
                        _state.update {
                            it.copy(topTracks = topTracksList, canLoadData = true)
                        }
                        
                    },
                    onError = {
                        _state.update {
                            it.copy(canLoadData = false)
                        }
                    }
                )
            }
            HomeIntent.LoadTopTracks -> fetchApiTopArtist(
                onSuccess = { topArtists ->
                    val topArtistsList = topArtists.map{ response ->
                        response.toTopArtist()
                    }
                    _state.update {
                        it.copy(topArtists = topArtistsList, canLoadData = true)
                    }
                },
                onError = {
                    _state.update {
                        it.copy(canLoadData = false)
                    }
                }
            )
        }
    }
    
    private fun fetchApiTopAlbum(
        onSuccess: (List<Album>) -> Unit,
        onError: () -> Unit,
    ) {
        val call = HomeScreenClient.buildTopAlbum().getTopAlbum()
        call.enqueue(
            object : Callback<TopAlbumsResponse> {
                override fun onResponse(
                    call: Call<TopAlbumsResponse>,
                    response: Response<TopAlbumsResponse>,
                ) {
                    if (response.isSuccessful) {
                        val response = response.body()?.topalbums?.album ?: emptyList()
                        onSuccess(response)
                        Log.d("TOP ALBUM","${response}")
                    } else {
                        onError()
                    }
                }

                override fun onFailure(call: Call<TopAlbumsResponse>, t: Throwable) {
                    Log.e("API", "Lỗi API: ${t.message}")
                    onError()
                }
            },
        )
    }

    private fun fetchApiTopArtist(
        onSuccess: (List<Artist>) -> Unit,
        onError: () -> Unit,
    ) {
        val call = HomeScreenClient.buildTopArtist().getTopArtist()
        call.enqueue(
            object : Callback<TopArtistsResponse> {
                override fun onResponse(
                    call: Call<TopArtistsResponse>,
                    response: Response<TopArtistsResponse>,
                ) {
                    if (response.isSuccessful) {
                        val list = response.body()?.artists?.artist ?: emptyList()
                        onSuccess(list)
                    } else {
                        onError()
                    }
                }

                override fun onFailure(call: Call<TopArtistsResponse>, t: Throwable) {
                    Log.e("API", "Lỗi API: ${t.message}")
                    onError()
                }
            },
        )
    }

    private fun fetchApiTopTrack(
        onSuccess: (List<Track>) -> Unit,
        onError: () -> Unit,
    ) {
        val call = HomeScreenClient.buildTopTracks().getTopTracks()
        call.enqueue(
            object : Callback<TopTracksResponse> {
                override fun onResponse(
                    call: Call<TopTracksResponse>,
                    response: Response<TopTracksResponse>,
                ) {
                    if (response.isSuccessful) {
                        val list = response.body()?.toptracks?.track?: emptyList()
                        onSuccess(list)
                    } else {
                        onError()
                    }
                }

                override fun onFailure(call: Call<TopTracksResponse>, t: Throwable) {
                    Log.e("API", "Lỗi API: ${t.message}")
                    onError()
                }
            },
        )
    }
}
