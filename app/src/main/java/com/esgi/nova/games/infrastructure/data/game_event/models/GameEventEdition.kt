package com.esgi.nova.games.infrastructure.data.game_event.models

import com.esgi.nova.games.ports.IGameEventEdition
import java.time.LocalDateTime
import java.util.*

data class GameEventEdition(override val eventId: UUID, override val linkTime: LocalDateTime?) : IGameEventEdition {
}