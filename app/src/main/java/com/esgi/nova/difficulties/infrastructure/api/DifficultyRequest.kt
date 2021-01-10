package com.esgi.nova.difficulties.infrastructure.api

import com.esgi.nova.api.CustomMediaType
import com.esgi.nova.difficulties.infrastructure.dto.TranslatedDifficultyDto
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Headers

interface DifficultyRequest {


    @Headers("Accept: ${CustomMediaType.Application.TranslatedDifficulty}")
    @GET("")
    fun getAllTranslatedDifficulties(@Field("language") language: String): Call<List<TranslatedDifficultyDto>>?
}