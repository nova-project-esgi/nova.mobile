package com.esgi.nova.games.application.models

import com.esgi.nova.games.ports.IGameEvent
import java.time.LocalDateTime
import java.util.*

data class GameEvent(
    override val eventId: UUID,
    override val gameId: UUID,
    override val linkTime: LocalDateTime
) : IGameEvent {
    override val id: UUID = UUID.randomUUID()
}