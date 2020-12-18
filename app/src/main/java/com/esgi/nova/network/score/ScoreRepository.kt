package com.esgi.nova.network.score

import com.esgi.nova.models.Score
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ScoreRepository {
    private var scoreRequest: ScoreRequest? = null;

    init
    {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://next.json-generator.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        scoreRequest = retrofit.create(ScoreRequest::class.java)
    }

    fun retrieveUser(callback: Callback<Score>)
    {
        val call = scoreRequest?.retrieveUser()
        call?.enqueue(callback)
    }
}