package com.esgi.nova.games.infrastructure.api

import com.esgi.nova.models.Score
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ScoreApiRepository @Inject constructor() {
    private var scoreRequest: ScoreRequest

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://next.json-generator.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        scoreRequest = retrofit.create(ScoreRequest::class.java)
    }

    fun retrieveUser(callback: Callback<Score>) {
        val call = scoreRequest.retrieveUser()
        call?.enqueue(callback)
    }
}