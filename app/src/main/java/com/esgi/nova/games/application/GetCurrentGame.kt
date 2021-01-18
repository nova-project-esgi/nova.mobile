package com.esgi.nova.games.application

import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.ports.IResumedGameWithResourceIcons
import com.esgi.nova.resources.application.GetAllImageResourceWrappers
import javax.inject.Inject

class GetCurrentGame @Inject constructor(
    private val gameDbRepository: GameDbRepository,
    private val getAllImageResourceWrappers: GetAllImageResourceWrappers
) {

    fun execute(): IResumedGameWithResourceIcons? =
        gameDbRepository.getActiveGameId()?.let { id ->
            gameDbRepository.getResumedGameById(id)?.let { resumedGame ->
                val resourceWrappers = getAllImageResourceWrappers.execute()
                return resumedGame.toResumedGameWithResourceIcons(resourceWrappers)
            }
        }

}

