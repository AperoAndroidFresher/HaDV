package com.example.dovietha_bt.api.homescreen.topalbum


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiTopAlbumService {
    @GET("?method=artist.getTopAlbums")
    fun getTopAlbum(
        @Query("api_key") apiKey: String = "e65449d181214f936368984d4f4d4ae8",
        @Query("format") format: String = "json",
        @Query("mbid") mbid: String = "f9b593e6-4503-414c-99a0-46595ecd2e23"
    ): Call<TopAlbumsResponse>

    @GET("?method=artist.getTopAlbums")
    fun getTop6Album(
        @Query("api_key") apiKey: String = "e65449d181214f936368984d4f4d4ae8",
        @Query("format") format: String = "json",
        @Query("mbid") mbid: String = "f9b593e6-4503-414c-99a0-46595ecd2e23",
        @Query("limit") limit: Int = 6
    ): Call<TopAlbumsResponse>
}
