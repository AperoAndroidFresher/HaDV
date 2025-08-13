package com.example.dovietha_bt.api.homescreen.topartist

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiTopArtistService {
    @GET("?method=chart.gettopartists")
    fun getTopArtist(
        @Query("api_key") apiKey: String = "e65449d181214f936368984d4f4d4ae8",
        @Query("format") format: String = "json"
    ): Call<TopArtistsResponse>
    
    @GET("?method=chart.gettopartists")
    fun getTop5Artist(
        @Query("api_key") apiKey: String = "e65449d181214f936368984d4f4d4ae8",
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 5
    ): Call<TopArtistsResponse>
}
