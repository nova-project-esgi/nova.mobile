package com.esgi.nova.difficulties.infrastructure.api

import com.esgi.nova.difficulties.infrastructure.dto.TranslatedDifficultyDto
import com.esgi.nova.infrastructure.api.apiBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import javax.inject.Inject


class DifficultyApiRepository @Inject constructor() {

    private var difficultiesRequest: DifficultiesRequests

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()
        difficultiesRequest = retrofit.create(DifficultiesRequests::class.java)
    }

    fun getAllTranslatedDifficulties(language: String): List<TranslatedDifficultyDto> {
        return difficultiesRequest.getAllTranslatedDifficulties(language = language)?.execute()?.body() ?: listOf()
    }
}


