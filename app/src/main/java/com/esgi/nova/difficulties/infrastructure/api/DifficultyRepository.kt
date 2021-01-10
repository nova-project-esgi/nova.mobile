package com.esgi.nova.difficulties.infrastructure.api

import com.esgi.nova.difficulties.infrastructure.dto.TranslatedDifficultyDto
import com.esgi.nova.events.infrastructure.api.EventRequest
import com.esgi.nova.events.infrastructure.dto.TranslatedEventsWithBackgroundDto
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DifficultyRepository {

    private var difficultyRequest: DifficultyRequest?= null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://freenetaccess.freeboxos.fr:8001/difficulties/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        difficultyRequest = retrofit.create(DifficultyRequest::class.java)
    }

    fun getAllTranslatedDifficulties(callback: Callback<List<TranslatedDifficultyDto>>)
    {
        val language = "en" // ???
        val call = difficultyRequest?.getAllTranslatedDifficulties(language = language)
        call?.enqueue(callback)
    }
}