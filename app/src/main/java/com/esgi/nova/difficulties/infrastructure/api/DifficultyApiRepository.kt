package com.esgi.nova.difficulties.infrastructure.api

import com.esgi.nova.difficulties.infrastructure.api.models.ResumedDifficulty
import com.esgi.nova.infrastructure.api.apiBuilder
import retrofit2.Retrofit
import javax.inject.Inject


class DifficultyApiRepository @Inject constructor() {

    private var difficultiesService: DifficultyService

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()
        difficultiesService = retrofit.create(DifficultyService::class.java)
    }

    fun getAllTranslatedDifficulties(language: String): List<ResumedDifficulty> {
        return difficultiesService
            .getAllTranslatedDifficulties(language = language)
            ?.execute()
            ?.body()
            ?.map { it -> it.toDifficultyWithResourceResumes() } ?: listOf()
    }
}


