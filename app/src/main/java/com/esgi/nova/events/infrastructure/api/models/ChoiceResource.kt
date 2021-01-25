package com.esgi.nova.events.infrastructure.api.models

import com.esgi.nova.events.ports.IChoiceResource
import java.util.*

class ChoiceResource(
    override val resourceId: UUID,
    override val choiceId: UUID,
    override val changeValue: Int
) :IChoiceResource