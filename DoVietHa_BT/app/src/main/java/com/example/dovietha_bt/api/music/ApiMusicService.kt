package com.example.dovietha_bt.api.music

import retrofit2.Call
import retrofit2.http.GET

interface ApiMusicService {
    @GET("techtrek/Remote_audio.json")
    fun getMusics(): Call<List<ApiMusic>>
}