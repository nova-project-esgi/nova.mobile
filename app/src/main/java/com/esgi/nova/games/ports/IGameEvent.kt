package com.esgi.nova.games.ports

import java.time.LocalDateTime
import java.util.*


interface IGameEvent {
    val eventId: UUID
    val gameId: UUID
    val linkTime: LocalDateTime
}