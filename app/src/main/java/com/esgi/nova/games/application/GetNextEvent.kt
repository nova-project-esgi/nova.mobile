package com.esgi.nova.games.application

import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import com.esgi.nova.events.ports.IDetailedEvent
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.infrastructure.data.game_event.GameEventDbRepository
import java.util.*
import javax.inject.Inject

class GetNextEvent @Inject constructor(
    private val gameDbRepository: GameDbRepository,
    private val gameEventDbRepository: GameEventDbRepository,
    private val eventDbRepository: EventDbRepository
){

    companion object{
        const val LastEventLimit = 3
    }

    fun execute(gameId: UUID): IDetailedEvent? {
        val gameEvents = gameEventDbRepository.getAllEventsByGameOrderByLinkTimeDesc(gameId)
        val events = eventDbRepository.getAll()

        gameDbRepository.getGameEditionById(gameId)
        return null
    }
}