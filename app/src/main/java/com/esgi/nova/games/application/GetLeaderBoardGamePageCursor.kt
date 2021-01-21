package com.esgi.nova.games.application

import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.games.ports.ILeaderBoardGameView
import com.esgi.nova.infrastructure.api.pagination.PageCursor
import com.esgi.nova.infrastructure.ports.IPageCursor
import java.util.*
import javax.inject.Inject

class GetLeaderBoardGamePageCursor @Inject constructor(private val gameApiRepository: GameApiRepository) {

    fun execute(difficultyId: UUID): IPageCursor<ILeaderBoardGameView> =
        PageCursor({ page, pageSize ->
            gameApiRepository.getDefaultGamesList(
                difficultyId,
                page,
                pageSize
            )
        })
}

