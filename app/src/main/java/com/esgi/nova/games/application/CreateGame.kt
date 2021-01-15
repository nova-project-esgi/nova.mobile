package com.esgi.nova.games.application

import com.esgi.nova.events.infrastructure.api.EventApiRepository
import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceDbRepository
import com.esgi.nova.events.infrastructure.data.choices.ChoiceDbRepository
import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import com.esgi.nova.files.application.SynchronizeFile
import com.esgi.nova.games.application.models.GameForCreation
import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.games.ports.IGameForCreation
import com.esgi.nova.infrastructure.fs.FsConstants
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import java.util.*
import javax.inject.Inject


class CreateGame @Inject constructor(
    private val gameApiRepository: GameApiRepository,
    private val gameDbRepository: GameDbRepository,
    private val userStorageRepository: UserStorageRepository
) {

    fun execute(difficultyId: UUID) {
        userStorageRepository.getUsername()?.let { username ->
            gameApiRepository.createGame(GameForCreation(username, difficultyId))?.let { game ->
                gameDbRepository.insertOne(game)
            }
        }
    }
}
