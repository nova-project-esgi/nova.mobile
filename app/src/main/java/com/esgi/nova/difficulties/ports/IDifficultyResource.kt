package com.esgi.nova.difficulties.ports

import com.esgi.nova.infrastructure.data.IIdEntity
import java.util.*

interface IDifficultyResource : IIdEntity<UUID> {
    val resourceId: UUID
    val difficultyId: UUID
    val startValue: Int
    override val id: UUID
        get() = difficultyId
}