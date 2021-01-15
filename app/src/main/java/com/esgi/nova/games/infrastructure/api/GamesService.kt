package com.esgi.nova.games.infrastructure.api

import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.infrastructure.api.HeaderConstants
import com.esgi.nova.infrastructure.api.pagination.PageMetadata
import com.esgi.nova.games.infrastructure.dto.LeaderBoardGameView
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GamesService {

    @Headers(HeaderConstants.Accept + ": " + ApiConstants.CustomMediaType.Application.LeaderBoardGame)
    @GET("${ApiConstants.BaseUrl}${ApiConstants.EndPoints.Games}")
    fun getDefaultGamesList(@Query("difficultyId") difficultyId: String): Call<PageMetadata<LeaderBoardGameView>>?
}