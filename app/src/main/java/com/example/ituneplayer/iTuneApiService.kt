package com.example.ituneplayer

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface iTuneApiService {
    @GET("WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit={n}/json")
    fun getTopSongs(@Path("n") num: Int) : Call<iTuneResponse>

    companion object{
        val api by lazy{
            Retrofit.Builder()
                .baseUrl("http://ax.itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(iTuneApiService::class.java)
        }
    }
}