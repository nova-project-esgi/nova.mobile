package com.esgi.nova.games.application

import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.ports.IRecappedGameWithResourceIcons
import com.esgi.nova.resources.application.GetAllImageResourceWrappers
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import javax.inject.Inject

class GetLastEndedGame @Inject constructor(
    private val gameDbRepository: GameDbRepository,
    private val getAllImageResourceWrappers: GetAllImageResourceWrappers,
    private val userStorageRepository: UserStorageRepository
) {

    fun execute(): IRecappedGameWithResourceIcons? =
        userStorageRepository.getUserId()?.let { userId ->
            gameDbRepository.getLastEndedGameId(userId)?.let { id ->
                gameDbRepository.getRecappedGameById(id)?.let { recappedGame ->
                    val resourceWrappers = getAllImageResourceWrappers.execute()
                    recappedGame.toRecappedGameWithResourceIcons(resourceWrappers)
                }
            }
        }

}

