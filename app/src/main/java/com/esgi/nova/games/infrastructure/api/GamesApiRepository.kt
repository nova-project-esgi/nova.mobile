package com.esgi.nova.games.infrastructure.api

import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.infrastructure.api.pagination.PageMetadata
import com.esgi.nova.games.infrastructure.dto.LeaderBoardGameView
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Inject

class GamesApiRepository @Inject constructor() {
    private var gamesRequest: GamesRequest

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("${ApiConstants.BaseUrl}${ApiConstants.EndPoints.Games}")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        gamesRequest = retrofit.create(GamesRequest::class.java)
    }

    fun getDefaultGamesList(difficultyId: UUID, token: String, callback: Callback<PageMetadata<LeaderBoardGameView>>) {
        val call = gamesRequest.getDefaultGamesList(token,difficultyId.toString())
        call?.enqueue(callback)
    }
}