package com.esgi.nova.difficulties.ports

import com.esgi.nova.infrastructure.data.IIdEntity
import java.util.*

interface IDifficulty : IIdEntity<UUID> {
    override val id: UUID
    val name: String
}