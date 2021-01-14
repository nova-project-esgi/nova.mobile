package com.esgi.nova.events.infrastructure.api.responses

import com.esgi.nova.events.infrastructure.api.models.ChoiceResource
import com.esgi.nova.events.infrastructure.api.models.ResumedChoice
import com.esgi.nova.events.infrastructure.api.responses.TranslatedChoiceResourceResponse
import com.esgi.nova.events.ports.IChoice
import com.esgi.nova.events.ports.IDetailedChoice
import java.util.*

data class TranslatedChoiceResponse(
    override val id: UUID,
    override val eventId: UUID,
    override val resources: MutableList<TranslatedChoiceResourceResponse>,
    override val title: String,
    override val description: String,
    val language: String
): IChoice, IDetailedChoice {

    fun toResumedChoice() = ResumedChoice(
        resources = resources.map { ChoiceResource(resourceId = it.id, changeValue = it.changeValue, choiceId = id) }.toMutableList(),
        description = description,
        eventId = eventId,
        id = id,
        title = title
    )

}