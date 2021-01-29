package com.esgi.nova.games.application

import android.util.Log
import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceDbRepository
import com.esgi.nova.games.application.models.Game
import com.esgi.nova.games.application.models.GameForCreation
import com.esgi.nova.games.exceptions.GameNotFoundException
import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceDbRepository
import com.esgi.nova.games.ports.IRecappedGameWithResourceIcons
import com.esgi.nova.infrastructure.api.exceptions.NoConnectionException
import com.esgi.nova.resources.application.GetAllImageResourceWrappers
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import java.util.*
import javax.inject.Inject


class CreateGame @Inject constructor(
    private val gameApiRepository: GameApiRepository,
    private val gameDbRepository: GameDbRepository,
    private val userStorageRepository: UserStorageRepository,
    private val gameResourceDbRepository: GameResourceDbRepository,
    private val difficultyResourceDbRepository: DifficultyResourceDbRepository,
    private val getAllImageResourceWrappers: GetAllImageResourceWrappers
) {

    suspend fun execute(difficultyId: UUID): IRecappedGameWithResourceIcons? {
        userStorageRepository.getUserResume()?.let { user ->
            difficultyResourceDbRepository.getDetailedDifficultyById(difficultyId)
                ?.let { difficulty ->

                    gameDbRepository.setActiveGamesEnded(userId = user.id).forEach { endedGameId ->
                        gameDbRepository.getGameEditionById(endedGameId)?.let { endedGame ->
                            try {
                                gameApiRepository.update(endedGameId, endedGame)
                            } catch (e: NoConnectionException) {
                                Log.i(CreateGame::class.qualifiedName, "Cannot update game on api")
                            } catch (e: GameNotFoundException) {
                                gameDbRepository.deleteById(endedGameId);
                            }
                        }
                    }

                    var dbGameId = gameDbRepository.insertOne(
                        Game(
                            difficultyId = difficultyId,
                            duration = 0,
                            isEnded = false,
                            id = UUID.randomUUID(),
                            userId = user.id
                        )
                    )
                    try {
                        gameApiRepository.createGame(GameForCreation(user.username, difficultyId))
                            ?.let { game ->
                                dbGameId = gameDbRepository.replace(dbGameId, game)
                            }
                    } catch (e: NoConnectionException) {
                        Log.i(CreateGame::class.qualifiedName, "Cannot create game on api")
                    }


                    gameResourceDbRepository.insertAll(difficulty.getGameResources(dbGameId))
                    val resourceWrappers = getAllImageResourceWrappers.execute()
                    gameDbRepository.getRecappedGameById(dbGameId)?.let { resumedGame ->
                        return resumedGame.toRecappedGameWithResourceIcons(resourceWrappers)
                    }
            }
        }
        return null
    }
}

