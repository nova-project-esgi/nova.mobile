package com.esgi.nova.games.infrastructure.api.models

import com.esgi.nova.games.ports.IGameEventState
import java.time.LocalDateTime
import java.util.*

data class GameEventState(override val eventId: UUID, override val linkTime: LocalDateTime) :
    IGameEventState