package com.example.dovietha_bt.api.homescreen.toptracks

import com.google.gson.annotations.SerializedName

data class TopTracksResponse(
    val toptracks: TopTracks
)

data class TopTracks(
    val track: List<Track>
)

data class Track(
    val name: String,
    val listeners: String,
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
