package com.esgi.nova.events.ports

import com.esgi.nova.infrastructure.data.IIdEntity
import java.util.*

interface IChoice: IIdEntity<UUID> {
    override val id: UUID
    val eventId: UUID
    val title: String
    val description: String
}

