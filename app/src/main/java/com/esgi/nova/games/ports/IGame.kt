package com.esgi.nova.games.ports

import com.esgi.nova.infrastructure.data.IIdEntity
import java.util.*

interface IGame : IIdEntity<UUID>{
    override val id: UUID
    val difficultyId: UUID
    val duration: Int
    val isEnded: Boolean

}