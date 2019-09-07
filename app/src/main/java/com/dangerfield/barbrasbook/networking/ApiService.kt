package com.dangerfield.barbrasbook.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    fun getLatest(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Call<Response>

}