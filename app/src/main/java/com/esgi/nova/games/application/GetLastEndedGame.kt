package com.esgi.nova.games.application

import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.ports.IRecappedGameWithResourceIcons
import com.esgi.nova.resources.application.GetAllImageResourceWrappers
import javax.inject.Inject

class GetLastEndedGame @Inject constructor(
    private val gameDbRepository: GameDbRepository,
    private val getAllImageResourceWrappers: GetAllImageResourceWrappers
) {

    fun execute(): IRecappedGameWithResourceIcons? =
        gameDbRepository.getLastEndedGameId()?.let { id ->
            gameDbRepository.getRecappedGameById(id)?.let { recappedGame ->
                val resourceWrappers = getAllImageResourceWrappers.execute()
                return recappedGame.toRecappedGameWithResourceIcons(resourceWrappers)
            }
        }

}

