package com.example.dovietha_bt.api.homescreen.topalbum

import com.google.gson.annotations.SerializedName

data class TopAlbumsResponse(
    val topalbums: TopAlbums
)

data class TopAlbums(
    val album: List<Album>
)

data class Album(
    val name: String,
    val artist: Artist,
    val image: List<Image>,
)

data class Artist(
    val name: String
)

data class Image(
    @SerializedName("#text")
    val url: String,
    val size: String
)

