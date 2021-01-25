package com.esgi.nova.games.infrastructure.data.game_event.models

import com.esgi.nova.games.ports.ILinkTimeEvent
import java.time.LocalDateTime
import java.util.*

data class LinkTimeEvent(
    override val id: UUID,
    override val title: String,
    override val description: String,
    override val linkTime: LocalDateTime
) : ILinkTimeEvent