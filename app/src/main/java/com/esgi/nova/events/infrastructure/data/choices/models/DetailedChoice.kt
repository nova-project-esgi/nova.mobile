package com.esgi.nova.events.infrastructure.data.choices.models

import com.esgi.nova.events.infrastructure.data.choice_resource.models.ChangeValueResource
import com.esgi.nova.events.ports.IDetailedChoice
import java.util.*

data class DetailedChoice(
    override val resources: MutableList<ChangeValueResource>,
    override val id: UUID,
    override val eventId: UUID,
    override val title: String,
    override val description: String
) : IDetailedChoice
