package com.example.dovietha_bt.api.homescreen.toptracks

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiTopTracksService {
    @GET("?method=artist.getTopTracks")
    fun getTopTracks(
        @Query("api_key") apiKey: String = "e65449d181214f936368984d4f4d4ae8",
        @Query("format") format: String = "json",
        @Query("mbid") mbid: String = "f9b593e6-4503-414c-99a0-46595ecd2e23"
    ): Call<TopTracksResponse>
    
    @GET("?method=artist.getTopTracks")
    fun getTop5Tracks(
        @Query("api_key") apiKey: String = "e65449d181214f936368984d4f4d4ae8",
        @Query("format") format: String = "json",
        @Query("mbid") mbid: String = "f9b593e6-4503-414c-99a0-46595ecd2e23",
        @Query("limit") limit: Int = 5
    ): Call<TopTracksResponse>
}
