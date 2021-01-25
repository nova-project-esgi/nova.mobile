package com.esgi.nova.events.infrastructure.data.choice_resource.models

import com.esgi.nova.events.ports.IDetailedChoice
import java.util.*

data class ChangeValueResource(
    override val id: UUID,
    override val name: String,
    override val changeValue: Int
) : IDetailedChoice.IChangeValueResource