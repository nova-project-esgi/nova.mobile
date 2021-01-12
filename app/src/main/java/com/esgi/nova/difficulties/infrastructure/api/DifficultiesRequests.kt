package com.esgi.nova.difficulties.infrastructure.api

import com.esgi.nova.difficulties.infrastructure.dto.TranslatedDifficultyDto
import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.infrastructure.api.HeaderConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DifficultiesRequests {


    @Headers("${HeaderConstants.Accept}: ${ApiConstants.CustomMediaType.Application.TranslatedDifficulty}")
    @GET("${ApiConstants.EndPoints.Difficulties}${ApiConstants.EndPoints.Load}")
    fun getAllTranslatedDifficulties(@Query("language") language: String): Call<List<TranslatedDifficultyDto>>?
}