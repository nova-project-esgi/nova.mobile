package com.esgi.nova.events.ports

import com.esgi.nova.infrastructure.data.IIdEntity
import java.util.*

interface IEvent : IIdEntity<UUID> {
    override val id: UUID
    val description: String
    val title: String
    val isDaily: Boolean
}