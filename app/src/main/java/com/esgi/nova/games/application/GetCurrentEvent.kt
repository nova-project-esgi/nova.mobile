package com.esgi.nova.games.application

import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import com.esgi.nova.events.ports.IDetailedEvent
import com.esgi.nova.files.application.GetFileBitmapById
import com.esgi.nova.files.dtos.FileWrapperDto
import com.esgi.nova.games.infrastructure.data.game_event.GameEventDbRepository
import com.esgi.nova.infrastructure.fs.FsConstants
import java.util.*
import javax.inject.Inject

class GetCurrentEvent @Inject constructor(
    private val eventDbRepository: EventDbRepository,
    private val gameEventDbRepository: GameEventDbRepository,
    private val getFileBitmapById: GetFileBitmapById
) {

    fun execute(gameId: UUID): FileWrapperDto<IDetailedEvent>? =
        gameEventDbRepository.getLastGameEventByGame(gameId)?.let { gameEvent ->
            eventDbRepository.getDetailedEventById(gameEvent.eventId)?.let { event ->
                getFileBitmapById.execute(FsConstants.Paths.Events, event.id)?.let { file ->
                    FileWrapperDto(event, file)
                }
            }
        }
}

