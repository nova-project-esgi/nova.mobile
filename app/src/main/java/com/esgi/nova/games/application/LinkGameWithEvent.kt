package com.esgi.nova.games.application

import com.esgi.nova.games.application.models.GameEvent
import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.infrastructure.data.game_event.GameEventDbRepository
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class LinkGameWithEvent @Inject constructor(
    private val gameApiRepository: GameApiRepository,
    private val gameDbRepository: GameDbRepository,
    private val gameEventDbRepository: GameEventDbRepository
) {

    fun execute(gameId: UUID, eventId: UUID) {
        gameEventDbRepository.insertOne(GameEvent(eventId, gameId, LocalDateTime.now()))
        gameDbRepository.getGameEditionById(gameId)?.let { gameEdition ->
            gameApiRepository.update(gameId, gameEdition)
        }
    }
}