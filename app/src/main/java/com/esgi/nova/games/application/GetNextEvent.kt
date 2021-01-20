package com.esgi.nova.games.application

import com.esgi.nova.events.application.GetDailyEvent
import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import com.esgi.nova.events.ports.IDetailedEvent
import com.esgi.nova.files.application.GetFileBitmapById
import com.esgi.nova.files.application.model.FileWrapper
import com.esgi.nova.files.infrastructure.fs.FileStorageRepository
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.application.models.GameEvent
import com.esgi.nova.games.infrastructure.data.game_event.GameEventDbRepository
import com.esgi.nova.infrastructure.fs.FsConstants
import com.esgi.nova.parameters.infrastructure.storage.ParametersStorageRepository
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class GetNextEvent @Inject constructor(
    private val gameEventDbRepository: GameEventDbRepository,
    private val eventDbRepository: EventDbRepository,
    private val getDailyEvent: GetDailyEvent,
    private val getFileBitmapById: GetFileBitmapById,
    private val parametersStorageRepository: ParametersStorageRepository

){

    companion object{
        const val LastEventLimit = 3
    }

    fun execute(gameId: UUID): IFileWrapper<IDetailedEvent>? {

        if(parametersStorageRepository.get().hasDailyEvents){
            getDailyEvent.execute(gameId)?.let { event ->
                gameEventDbRepository.insertOne(
                    GameEvent(
                        eventId = event.id,
                        gameId = gameId,
                        linkTime = LocalDateTime.now(ZoneOffset.UTC)
                    )
                )
                getFileBitmapById.execute(FsConstants.Paths.Events, event.id)?.let { img ->
                    return FileWrapper(event, img)
                }
            }
        }

        val events = eventDbRepository.getAllNonDaily()

        if (events.isEmpty()) return null

        val gameEvents = gameEventDbRepository.getAllEventsByGameOrderByLinkTimeDesc(gameId)
        val eventLimit = if (LastEventLimit > events.size) LastEventLimit else events.size - 1
        var retry = true
        var selectedEventId: UUID? = null

        while (retry) {
            val idx = Random.nextInt(0, events.size)
            selectedEventId = events[idx].id
            retry = gameEvents.take(eventLimit).any { gameEvent -> gameEvent.eventId == selectedEventId }
        }

        selectedEventId?.let {
            gameEventDbRepository.insertOne(element = GameEvent(
                eventId = selectedEventId,
                gameId = gameId,
                linkTime = LocalDateTime.now(ZoneOffset.UTC)
            ))
            eventDbRepository.getDetailedEventById(id = selectedEventId)?.let { event ->
                getFileBitmapById.execute(FsConstants.Paths.Events, event.id)?.let { img ->
                    return FileWrapper(event, img)
                }
            }
        }

        return null
    }
}