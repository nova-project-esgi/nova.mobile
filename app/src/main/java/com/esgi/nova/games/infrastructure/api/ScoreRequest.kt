package com.esgi.nova.games.infrastructure.api

import com.esgi.nova.models.Score
import retrofit2.Call
import retrofit2.http.GET

interface ScoreRequest {
    @GET("json/get/Ey-Bp0E3F?delay=2000")
    fun retrieveUser(): Call<Score>?
}