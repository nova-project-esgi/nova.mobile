package com.esgi.nova.games.infrastructure.api

import com.esgi.nova.games.application.models.GameForCreation
import com.esgi.nova.games.infrastructure.api.models.GameForUpdate
import com.esgi.nova.games.ports.IGameForCreation
import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.models.Score
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface GameService {
    @GET("json/get/Ey-Bp0E3F?delay=2000")
    fun retrieveUser(): Call<Score>?

    @POST(ApiConstants.EndPoints.Games)
    fun createGame(@Body gameForCreation: GameForCreation): Call<ResponseBody>

    @PUT("${ApiConstants.EndPoints.Games}{id}")
    fun uploadGame(@Path("id") id: UUID, @Body gameForUpdate: GameForUpdate ): Call<ResponseBody>
}