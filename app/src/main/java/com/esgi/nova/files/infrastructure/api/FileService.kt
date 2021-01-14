package com.esgi.nova.files.infrastructure.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


interface FileService {

    @Streaming
    @GET
    fun getFile(@Url url: String): Call<ResponseBody>
}