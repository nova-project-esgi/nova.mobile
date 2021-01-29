package com.esgi.nova.games.application

import android.util.Log
import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceDbRepository
import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.games.application.models.Game
import com.esgi.nova.games.application.models.GameForCreation
import com.esgi.nova.games.application.models.GameResource
import com.esgi.nova.games.exceptions.GameNotFoundException
import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceDbRepository
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.infrastructure.api.exceptions.NoConnectionException
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import com.esgi.nova.users.ports.IUserRecapped
import java.util.*
import javax.inject.Inject


class ConfirmChoice @Inject constructor(
    private val gameDbRepository: GameDbRepository,
    private val gameApiRepository: GameApiRepository,
    private val gameResourceDbRepository: GameResourceDbRepository,
    private val choiceResourceDbRepository: ChoiceResourceDbRepository,
    private val userRepository: UserStorageRepository
) {

    suspend fun execute(gameId: UUID, choiceId: UUID, duration: Int): Boolean {
        var isEnded = false
        val user = userRepository.getUserResume() ?: return true

        gameDbRepository.getById(gameId)?.let { game ->
            choiceResourceDbRepository.getDetailedChoiceById(choiceId)?.let { choice ->
                isEnded = updateGameResources(choice, gameId, isEnded)
                updateGameToDb(gameId, game, isEnded, duration, user)
                updateGameToApi(gameId, user, game)
            }
        }
        return isEnded
    }

    private suspend fun updateGameToDb(
        gameId: UUID,
        game: IGame,
        isEnded: Boolean,
        duration: Int,
        user: IUserRecapped
    ) {
        gameDbRepository.update(
            Game(
                id = gameId,
                difficultyId = game.difficultyId,
                isEnded = isEnded,
                duration = duration,
                userId = user.id
            )
        )
    }

    private suspend fun updateGameResources(
        choice: IDetailedChoice,
        gameId: UUID,
        isEnded: Boolean
    ): Boolean {
        var isEnded1 = isEnded
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
                        isEnded1 = true
                    }
                }
        }
        return isEnded1
    }

    private suspend fun updateGameToApi(
        gameId: UUID,
        user: IUserRecapped,
        game: IGame
    ) {
        gameDbRepository.getGameEditionById(gameId)?.let { gameEdition ->
            try {
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
            } catch (e: NoConnectionException) {
                Log.i(ConfirmChoice::class.qualifiedName, "No connection for updating game")
            }
        }
    }
}

