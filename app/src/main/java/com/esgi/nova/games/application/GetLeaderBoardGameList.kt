package com.esgi.nova.games.application

import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.infrastructure.api.pagination.PageMetadata
import com.esgi.nova.games.infrastructure.api.models.LeaderBoardGameView
import java.util.*
import javax.inject.Inject

class GetLeaderBoardGameList @Inject constructor(private val gameApiRepository: GameApiRepository) {

    suspend fun execute(difficultyId: UUID, page: Int, pageSize: Int): PageMetadata<LeaderBoardGameView>? {
        return gameApiRepository.getDefaultGamesList(difficultyId, page, pageSize)
    }
}

