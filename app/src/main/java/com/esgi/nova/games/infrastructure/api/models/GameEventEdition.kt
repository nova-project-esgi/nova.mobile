package com.esgi.nova.games.infrastructure.api.models

import com.esgi.nova.games.ports.IGameEventEdition
import java.time.LocalDateTime
import java.util.*

class GameEventEdition(override val eventId: UUID, override val linkTime: LocalDateTime?) :
    IGameEventEdition