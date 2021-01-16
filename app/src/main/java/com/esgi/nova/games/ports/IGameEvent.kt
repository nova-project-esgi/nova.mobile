package com.esgi.nova.games.ports

import com.esgi.nova.infrastructure.data.IIdEntity
import java.time.LocalDateTime
import java.util.*


interface IGameEvent: IIdEntity<UUID> {
    val eventId: UUID
    val gameId: UUID
    val linkTime: LocalDateTime
    override val id: UUID
        get() = gameId
}