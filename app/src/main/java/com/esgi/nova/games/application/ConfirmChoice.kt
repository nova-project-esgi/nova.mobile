package com.esgi.nova.games.application

import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceDbRepository
import com.esgi.nova.games.application.models.Game
import com.esgi.nova.games.application.models.GameForCreation
import com.esgi.nova.games.application.models.GameResource
import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.games.infrastructure.api.exceptions.GameNotFoundException
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceDbRepository
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import org.jetbrains.anko.doAsync
import java.util.*
import javax.inject.Inject


class ConfirmChoice @Inject constructor(
    private val gameDbRepository: GameDbRepository,
    private val gameApiRepository: GameApiRepository,
    private val gameResourceDbRepository: GameResourceDbRepository,
    private val choiceResourceDbRepository: ChoiceResourceDbRepository,
    private val userRepository: UserStorageRepository
) {

    fun execute(gameId: UUID, choiceId: UUID, duration: Int): Boolean {
        var isEnded = false
        val user = userRepository.getUserResume() ?: return true

        gameDbRepository.getById(gameId)?.let { game ->
            choiceResourceDbRepository.getDetailedChoiceById(choiceId)?.let { choice ->
                choice.resources.forEach { resource ->
                    gameResourceDbRepository.getByResourceIdAndGameId(gameId, resource.id)
                        ?.let { gameResource ->
                            var updateResourceValue = gameResource.total + resource.changeValue
                            if (updateResourceValue < 0) {
                                updateResourceValue = 0
                            }
                            gameResourceDbRepository.update(
                                GameResource(
                                    resourceId = resource.id,
                                    gameId = gameId,
                                    total = updateResourceValue
                                )
                            )
                            if (updateResourceValue <= 0) {
                                isEnded = true
                            }
                        }
                }

                try {
                    gameDbRepository.update(
                        Game(
                            id = gameId,
                            difficultyId = game.difficultyId,
                            isEnded = isEnded,
                            duration = duration,
                            userId = user.id
                        )
                    )

                } catch (e: Exception) {
                    println(e)
                }

                gameDbRepository.getGameEditionById(gameId)?.let { gameEdition ->
                    doAsync {
                        try {
                            gameApiRepository.update(gameId, gameEdition)
                        } catch (e: GameNotFoundException) {
                            gameApiRepository.createGame(
                                GameForCreation(
                                    username = user.username,
                                    difficultyId = game.difficultyId
                                )
                            )
                            gameApiRepository.update(gameId, gameEdition)
                        }
                    }
                }

            }
        }
        return isEnded
    }
}

