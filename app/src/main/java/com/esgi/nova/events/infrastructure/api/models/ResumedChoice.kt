package com.esgi.nova.events.infrastructure.api.models

import com.esgi.nova.events.ports.IChoiceResource
import com.esgi.nova.events.ports.IResumedChoice
import java.util.*

class ResumedChoice(
    override val resources: MutableList<out IChoiceResource>,
    override val id: UUID,
    override val eventId: UUID,
    override val title: String,
    override val description: String
) : IResumedChoice