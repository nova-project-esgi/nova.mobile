package com.esgi.nova.resources.ports

import com.esgi.nova.infrastructure.data.IIdEntity
import java.util.*

interface IResource : IIdEntity<UUID> {
    override val id: UUID
    val name: String?
}