package com.example.dovietha_bt.api.homescreen

import com.example.dovietha_bt.api.homescreen.topalbum.ApiTopAlbumService
import com.example.dovietha_bt.api.homescreen.topartist.ApiTopArtistService
import com.example.dovietha_bt.api.homescreen.toptracks.ApiTopTracksService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HomeScreenClient {
    private const val BASE_URL = "https://ws.audioscrobbler.com/2.0/"
    private const val REQUEST_TIMEOUT = 30L

    private val retrofit by lazy{buildRetrofit()}
    fun buildTopAlbum(): ApiTopAlbumService{return retrofit.create(ApiTopAlbumService::class.java)}
    fun buildTopArtist(): ApiTopArtistService{return retrofit.create(ApiTopArtistService::class.java)}
    fun buildTopTracks(): ApiTopTracksService {return retrofit.create(ApiTopTracksService::class.java)}
    private fun buildRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildClient())
            .addConverterFactory(GsonConverterFactory.create(gsonConfig))
            .build()
    }
    private fun buildClient(): OkHttpClient{
        return OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT,TimeUnit.SECONDS)
            .build()
    }
    private var gsonConfig = GsonBuilder().create()
}
