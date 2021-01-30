package com.esgi.nova.resources.infrastructure.api

import com.esgi.nova.infrastructure.api.ApiConstants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ResourceService {

    @Headers("Accept: ${ApiConstants.CustomMediaType.Application.TranslatedResource}")
    @GET("${ApiConstants.EndPoints.Resources}${ApiConstants.EndPoints.Load}")
    suspend fun getAll(@Query("language") language: String): List<TranslatedResourceResponse>
}