package com.esgi.nova.games.ports

import java.time.LocalDateTime
import java.util.*

interface ILinkTimeEvent {
    val id: UUID
    val title: String
    val description: String
    val linkTime: LocalDateTime
}