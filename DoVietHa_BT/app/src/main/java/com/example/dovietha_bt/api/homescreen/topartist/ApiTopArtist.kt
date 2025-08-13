package com.example.dovietha_bt.api.homescreen.topartist

import com.google.gson.annotations.SerializedName

data class TopArtistsResponse(
    val artists: Artists
)

data class Artists(
    val artist: List<Artist>
)

data class Artist(
    val name: String,
    val image: List<Image>
)

data class Image(
    @SerializedName("#text")
    val url: String,
    val size: String
)
