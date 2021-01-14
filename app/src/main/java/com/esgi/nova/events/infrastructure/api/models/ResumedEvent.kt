package com.esgi.nova.events.infrastructure.api.models

import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.events.ports.IResumedChoice
import com.esgi.nova.events.ports.IResumedEvent
import java.util.*

data class ResumedEvent(
    override val choices: MutableList<out IResumedChoice>,
    override val id: UUID,
    override val description: String,
    override val title: String
) : IResumedEvent{

}