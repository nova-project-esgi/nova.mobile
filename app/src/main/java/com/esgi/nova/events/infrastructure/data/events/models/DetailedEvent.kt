package com.esgi.nova.events.infrastructure.data.events.models

import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.events.ports.IDetailedEvent
import java.util.*

data class DetailedEvent(
    override val choices: MutableList<out IDetailedChoice>,
    override val id: UUID,
    override val description: String,
    override val title: String
) : IDetailedEvent {
}