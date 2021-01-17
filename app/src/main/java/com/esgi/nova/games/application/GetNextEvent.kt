package com.esgi.nova.games.application

import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import com.esgi.nova.events.ports.IDetailedEvent
import com.esgi.nova.games.application.models.GameEvent
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.infrastructure.data.game_event.GameEventDbRepository
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class GetNextEvent @Inject constructor(
    private val gameDbRepository: GameDbRepository,
    private val gameEventDbRepository: GameEventDbRepository,
    private val eventDbRepository: EventDbRepository
){

    companion object{
        const val LastEventLimit = 3
    }

    fun execute(gameId: UUID): IDetailedEvent? {

        val events = eventDbRepository.getAll()
        if (events.isEmpty()) return null

        val gameEvents = gameEventDbRepository.getAllEventsByGameOrderByLinkTimeDesc(gameId)
        val eventLimit = if (LastEventLimit > events.size) LastEventLimit else events.size - 1
        var retry = true
        var selectedEventId: UUID? = null

        while(retry){
            val idx = Random.nextInt(0, events.size)
            selectedEventId = events[idx].id
            retry = gameEvents.take(eventLimit).any { gameEvent -> gameEvent.eventId == selectedEventId }
        }

        selectedEventId?.let {
            gameEventDbRepository.insertOne(GameEvent(selectedEventId, gameId, LocalDateTime.now()))
            return eventDbRepository.getDetailedEventById(selectedEventId)
        }

        return null
    }
}