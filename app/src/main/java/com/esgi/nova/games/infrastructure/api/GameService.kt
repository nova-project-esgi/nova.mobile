package com.esgi.nova.games.infrastructure.api

import com.esgi.nova.games.ports.IGameForCreation
import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.models.Score
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GameService {
    @GET("json/get/Ey-Bp0E3F?delay=2000")
    fun retrieveUser(): Call<Score>?

    @POST(ApiConstants.EndPoints.Games)
    fun createGame(@Body gameForCreation: IGameForCreation): Call<Any>
}