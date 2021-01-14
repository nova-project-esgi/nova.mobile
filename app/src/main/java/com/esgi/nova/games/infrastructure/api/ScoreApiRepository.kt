package com.esgi.nova.games.infrastructure.api

import com.esgi.nova.models.Score
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ScoreApiRepository @Inject constructor() {
    private var scoreService: ScoreService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://next.json-generator.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        scoreService = retrofit.create(ScoreService::class.java)
    }

    fun retrieveUser(callback: Callback<Score>) {
        val call = scoreService.retrieveUser()
        call?.enqueue(callback)
    }
}