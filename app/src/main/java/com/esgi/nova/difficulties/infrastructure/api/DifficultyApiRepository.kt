package com.esgi.nova.difficulties.infrastructure.api

import com.esgi.nova.NovaApplication
import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.difficulties.infrastructure.dto.TranslatedDifficultyDto
import com.esgi.nova.infrastructure.api.AuthorizationInterceptor
import com.esgi.nova.infrastructure.api.apiBuilder
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class DifficultyApiRepository @Inject constructor() {

    private var difficultyRequest: DifficultyRequest

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()
        difficultyRequest = retrofit.create(DifficultyRequest::class.java)
    }

    fun getAllTranslatedDifficulties(callback: Callback<List<TranslatedDifficultyDto>>)
    {
        val language = "en" // ???
        val call = difficultyRequest.getAllTranslatedDifficulties(language = language)
        call?.enqueue(callback)
    }
}