package com.esgi.nova.games.infrastructure.api

import com.esgi.nova.infrastructure.api.pagination.PageMetadata
import com.esgi.nova.games.infrastructure.dto.LeaderBoardGameView
import com.esgi.nova.infrastructure.api.apiBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject

class GamesApiRepository @Inject constructor() {
    private var gamesService: GamesService

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()

        gamesService = retrofit.create(GamesService::class.java)
    }

    fun getDefaultGamesList(difficultyId: UUID, callback: Callback<PageMetadata<LeaderBoardGameView>>) {
        val call = gamesService.getDefaultGamesList(difficultyId.toString())
        call?.enqueue(callback)
    }
}