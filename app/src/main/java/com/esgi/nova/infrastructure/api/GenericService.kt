package com.esgi.nova.infrastructure.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface GenericService {

    @GET
    suspend fun get(@Url url: String): ResponseBody
}