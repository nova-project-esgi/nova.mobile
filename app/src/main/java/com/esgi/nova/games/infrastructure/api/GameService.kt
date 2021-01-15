package com.esgi.nova.games.infrastructure.api

import com.esgi.nova.games.infrastructure.dto.LeaderBoardGameView
import com.esgi.nova.games.ports.IGameForCreation
import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.infrastructure.api.HeaderConstants
import com.esgi.nova.infrastructure.api.pagination.PageMetadata
import com.esgi.nova.models.Score
import retrofit2.Call
import retrofit2.http.*

interface GameService {

    @POST(ApiConstants.EndPoints.Games)
    fun createGame(@Body gameForCreation: IGameForCreation): Call<Any>

    @Headers(HeaderConstants.Accept + ": " + ApiConstants.CustomMediaType.Application.LeaderBoardGame)
    @GET("${ApiConstants.BaseUrl}${ApiConstants.EndPoints.Games}")
    fun getLeaderBoardGamesByDifficulty(@Query("difficultyId") difficultyId: String): Call<PageMetadata<LeaderBoardGameView>>
}