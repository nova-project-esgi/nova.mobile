package com.esgi.nova.events.infrastructure.api.responses

import com.esgi.nova.events.ports.IDetailedChoice
import java.util.*


data class TranslatedChoiceResourceResponse(
    override val id: UUID,
    val language: String,
    override val name: String,
    override val changeValue: Int
): IDetailedChoice.IChangeValueResource