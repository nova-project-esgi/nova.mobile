package com.esgi.nova.games.application

import android.util.Log
import com.esgi.nova.games.application.models.GameForCreation
import com.esgi.nova.games.exceptions.GameNotFoundException
import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.games.ports.IGameEdition
import com.esgi.nova.infrastructure.api.error_handling.ApiException
import com.esgi.nova.infrastructure.api.exceptions.NoConnectionException
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class UpdateGameToApi @Inject constructor(
    private val gameDbRepository: GameDbRepository,
    private val gameApiRepository: GameApiRepository,
    private val userStorageRepository: UserStorageRepository,
) {
    suspend fun execute(
        gameId: UUID
    ) {
        gameDbRepository.getById(gameId)?.let { game ->
            userStorageRepository.getUsername()?.let { username ->
                gameDbRepository.getGameEditionById(gameId)?.let { gameEdition ->
                    GlobalScope.launch(Dispatchers.IO) {
                        try {
                            try {
                                gameApiRepository.update(gameId, gameEdition)
                            } catch (e: GameNotFoundException) {
                                recreateGame(username, game, gameId, gameEdition)
                            } catch (e: ApiException) {
                                recreateGame(username, game, gameId, gameEdition)
                            }
                        } catch (e: NoConnectionException) {
                            Log.i(
                                ConfirmChoice::class.qualifiedName,
                                "No connection for updating game"
                            )
                        } catch (e: ApiException) {
                            Log.i(
                                ConfirmChoice::class.qualifiedName,
                                "Unexpected api exception"
                            )
                        }
                    }

                }
            }
        }

    }

    private suspend fun recreateGame(
        username: String,
        game: IGame,
        gameId: UUID,
        gameEdition: IGameEdition
    ) {
        gameApiRepository.createGame(
            GameForCreation(
                username = username,
                difficultyId = game.difficultyId
            )
        )
        gameApiRepository.update(gameId, gameEdition)
    }
}