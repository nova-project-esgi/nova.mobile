package com.esgi.nova.games.ports

import com.esgi.nova.infrastructure.data.IIdEntity
import java.util.*

interface IGameResource : IIdEntity<UUID> {
    val resourceId: UUID
    val gameId: UUID
    val total: Int
    override val id: UUID
        get() = gameId
}