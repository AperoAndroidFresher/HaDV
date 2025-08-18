package com.example.dovietha_bt.api.music

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiMusicClient {
    private const val BASE_URL = "https://static.apero.vn"
    private const val REQUEST_TIMEOUT = 30L

    private val retrofit by lazy{buildRetrofit()}
    fun build(): ApiMusicService{return retrofit.create(ApiMusicService::class.java)}
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