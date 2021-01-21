package com.esgi.nova.games.application

import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.games.ports.ILeaderBoardGameView
import com.esgi.nova.infrastructure.api.pagination.IGetPage
import java.util.*
import javax.inject.Inject

class GetLeaderBoardGamePageFunc @Inject constructor(private val gameApiRepository: GameApiRepository) {

    fun execute(difficultyId: UUID): IGetPage<ILeaderBoardGameView> = IGetPage<ILeaderBoardGameView> { page, pageSize ->
        gameApiRepository.getDefaultGamesList(
            difficultyId,
            page,
            pageSize
        )
    }
}

