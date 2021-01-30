package com.esgi.nova.languages.infrastructure.api

import com.esgi.nova.infrastructure.api.ApiConstants
import retrofit2.http.GET

interface LanguageService {

    @GET("${ApiConstants.EndPoints.Languages}${ApiConstants.EndPoints.Load}")
    suspend fun getAll(): List<LanguageResponse>
}

