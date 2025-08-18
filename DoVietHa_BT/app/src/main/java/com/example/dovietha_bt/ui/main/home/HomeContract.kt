package com.example.dovietha_bt.ui.main.home

import com.example.dovietha_bt.api.homescreen.topalbum.Album
import com.example.dovietha_bt.api.homescreen.topartist.Artist
import com.example.dovietha_bt.api.homescreen.toptracks.TopTracksResponse
import com.example.dovietha_bt.api.homescreen.toptracks.Track

data class TopAlbum(
    val name: String,
    val artist: String,
    val imageUrl: String,
)

data class TopTrack(
    val name: String,
    val listener: String,
    val artist: String,
    val imageUrl: String,
)

data class TopArtist(
    val name: String,
    val imageUrl: String,
)

data class HomeState(
    val topAlbums: List<TopAlbum> = emptyList(),
    val topTracks: List<TopTrack> = emptyList(),
    val topArtists: List<TopArtist> = emptyList(),
    val canLoadData: Boolean = true
)

sealed class HomeIntent {
    object LoadTopAlbums : HomeIntent()
    object LoadTopTracks : HomeIntent()
    object LoadTopArtists : HomeIntent()
}

fun Album.toTopAlbum(): TopAlbum {
    return TopAlbum(
        name = this.name,
        artist = this.artist.name,
        imageUrl = this.image[0].url,
    )
}

fun Artist.toTopArtist(): TopArtist {
    return TopArtist(
        name = this.name,
        imageUrl = this.image[1].url
    )
}

fun Track.toTopTrack(): TopTrack {
    return TopTrack(
        name = name,
        listener = listeners,
        artist = artist.name,
        imageUrl = image[1].url
    )
}
