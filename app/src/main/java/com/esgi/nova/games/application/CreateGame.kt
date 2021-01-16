package com.esgi.nova.games.application

import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceDbRepository
import com.esgi.nova.games.application.models.Game
import com.esgi.nova.games.application.models.GameForCreation
import com.esgi.nova.games.application.models.GameResource
import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceDbRepository
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.resources.infrastructure.data.ResourceDbRepository
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import java.util.*
import javax.inject.Inject


class CreateGame @Inject constructor(
    private val gameApiRepository: GameApiRepository,
    private val gameDbRepository: GameDbRepository,
    private val userStorageRepository: UserStorageRepository,
    private val gameResourceDbRepository: GameResourceDbRepository,
    private val difficultyResourceDbRepository: DifficultyResourceDbRepository
) {

    fun execute(difficultyId: UUID): IGame? {
        userStorageRepository.getUsername()?.let { username ->
            difficultyResourceDbRepository.getDetailedDifficultyById(difficultyId)?.let {difficulty ->
                var dbGameId = gameDbRepository.insertOne(
                    Game(difficultyId = difficultyId, duration = 0, isEnded = false, id = UUID.randomUUID())
                )
                gameApiRepository.createGame(GameForCreation(username, difficultyId))?.let {game ->
                    dbGameId = gameDbRepository.replace(dbGameId, game)
                }
                gameResourceDbRepository.insertAll(difficulty.getGameResources(dbGameId))
                return gameDbRepository.getById(dbGameId)
            }
        }
        return null
    }
}

