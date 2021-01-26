package com.esgi.nova.games.infrastructure.api

import com.esgi.nova.games.application.models.GameForCreation
import com.esgi.nova.games.infrastructure.api.models.GameForUpdate
import com.esgi.nova.games.infrastructure.api.models.GameState
import com.esgi.nova.games.infrastructure.api.models.LeaderBoardGameView
import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.infrastructure.api.HttpConstants
import com.esgi.nova.infrastructure.api.pagination.PageMetadata
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface GameService {

    @Headers(HttpConstants.Headers.Accept + ": " + ApiConstants.CustomMediaType.Application.LeaderBoardGame)
    @GET("${ApiConstants.BaseUrl}${ApiConstants.EndPoints.Games}")
    suspend fun getLeaderBoardGamesByDifficulty(
        @Query("difficultyId") difficultyId: String,
        @Query("page") page: Int?,
        @Query("size") pageSize: Int?
    ): PageMetadata<LeaderBoardGameView>

    @Headers(HttpConstants.Headers.Accept + ": " + ApiConstants.CustomMediaType.Application.GameState)
    @GET("${ApiConstants.BaseUrl}${ApiConstants.EndPoints.Games}")
    suspend fun getGameStateByUsername(@Query("username") username: String): GameState

    @POST(ApiConstants.EndPoints.Games)
    suspend fun createGame(@Body gameForCreation: GameForCreation): Response<*>

    @PUT("${ApiConstants.EndPoints.Games}{id}")
    suspend fun uploadGame(@Path("id") id: UUID, @Body gameForUpdate: GameForUpdate ): ResponseBody
}