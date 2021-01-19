package com.esgi.nova.games.ports

import java.time.LocalDateTime
import java.util.*

interface IGameEventState{
    val eventId: UUID
    val linkTime: LocalDateTime
}