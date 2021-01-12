package com.esgi.nova.resources.infrastructure.api

import com.esgi.nova.difficulties.infrastructure.dto.DifficultyResourceDto
import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.languages.infrastructure.dto.LanguageDto
import com.esgi.nova.resources.dto.TranslatedResourceDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ResourcesRequests {

    @Headers("Accept: ${ApiConstants.CustomMediaType.Application.TranslatedResource}")
    @GET("${ApiConstants.EndPoints.Resources}${ApiConstants.EndPoints.Load}")
    fun getAll(): Call<List<TranslatedResourceDto>>?
}