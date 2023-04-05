package com.example.searchgifs.network

import com.example.searchgifs.network.responses.GifsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("gifs/trending") // For future using
    suspend fun getQueryImages(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0
    ): GifsResponse

    @GET("gifs/search")
    suspend fun searchGifs(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0
    ): GifsResponse


}