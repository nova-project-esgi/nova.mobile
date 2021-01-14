package com.esgi.nova.users.application

import com.esgi.nova.games.infrastructure.api.GamesApiRepository
import com.esgi.nova.infrastructure.api.pagination.PageMetadata
import com.esgi.nova.games.infrastructure.dto.LeaderBoardGameView
import retrofit2.Callback
import java.util.*
import javax.inject.Inject

class GetDefaultGameList @Inject constructor(private val gamesApiRepository: GamesApiRepository) {

    fun execute(difficultyId: UUID,token: String, callback: Callback<PageMetadata<LeaderBoardGameView>>){
        gamesApiRepository.getDefaultGamesList(difficultyId, token, callback)
    }
}