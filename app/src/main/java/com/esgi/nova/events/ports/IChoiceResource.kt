package com.esgi.nova.events.ports

import com.esgi.nova.infrastructure.data.IIdEntity
import java.util.*

interface IChoiceResource : IIdEntity<UUID> {
    val resourceId: UUID
    val choiceId: UUID
    val changeValue: Int
    override val id: UUID
        get() = choiceId
}