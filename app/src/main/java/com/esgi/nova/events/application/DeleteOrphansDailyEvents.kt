package com.esgi.nova.events.application

import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import com.esgi.nova.games.infrastructure.data.game_event.GameEventDbRepository
import com.esgi.nova.ports.Synchronize
import javax.inject.Inject

class DeleteOrphansDailyEvents @Inject constructor(
    private val eventDbRepository: EventDbRepository,
    private val gameEventDbRepository: GameEventDbRepository
) : Synchronize {

    override suspend fun execute() {
        eventDbRepository.getAllDailyEvents().forEach { dailyEvent ->
            if(!gameEventDbRepository.eventHasGame(eventId = dailyEvent.id)){
                eventDbRepository.delete(dailyEvent)
            }
        }
    }
}