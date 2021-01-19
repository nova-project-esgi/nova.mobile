package com.esgi.nova.games.application

import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceDbRepository
import com.esgi.nova.games.application.models.Game
import com.esgi.nova.games.application.models.GameForCreation
import com.esgi.nova.games.application.models.ResumedGameWithResourceIcons
import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceDbRepository
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

    fun execute(difficultyId: UUID): ResumedGameWithResourceIcons? {
        userStorageRepository.getUserResume()?.let { user ->
            difficultyResourceDbRepository.getDetailedDifficultyById(difficultyId)
                ?.let { difficulty ->

                    gameDbRepository.setActiveGamesEnded(userId = user.id).forEach { endedGameId ->
                        gameDbRepository.getGameEditionById(endedGameId)?.let { endedGame ->
                            gameApiRepository.update(endedGameId, endedGame)
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

                    gameApiRepository.createGame(GameForCreation(user.username, difficultyId))
                        ?.let { game ->
                            dbGameId = gameDbRepository.replace(dbGameId, game)
                        }

                gameResourceDbRepository.insertAll(difficulty.getGameResources(dbGameId))
                val resourceWrappers = getAllImageResourceWrappers.execute()
                gameDbRepository.getResumedGameById(dbGameId)?.let {resumedGame ->
                    return resumedGame.toResumedGameWithResourceIcons(resourceWrappers)
                }
            }
        }
        return null
    }
}

