package com.esgi.nova.infrastructure.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface GenericService {

    @Streaming
    @GET
    fun <T> get(@Url url: String): Call<T>
}