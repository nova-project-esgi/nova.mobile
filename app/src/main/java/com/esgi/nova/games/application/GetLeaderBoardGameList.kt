package com.esgi.nova.games.application

import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.infrastructure.api.pagination.PageMetadata
import com.esgi.nova.games.infrastructure.dto.LeaderBoardGameView
import java.util.*
import javax.inject.Inject

class GetLeaderBoardGameList @Inject constructor(private val gameApiRepository: GameApiRepository) {

    fun execute(difficultyId: UUID): PageMetadata<LeaderBoardGameView>? {
        return gameApiRepository.getDefaultGamesList(difficultyId)
    }
}