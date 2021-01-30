package com.esgi.nova.events

import com.esgi.nova.events.infrastructure.api.responses.TranslatedChoiceResponse
import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceEntity

val TranslatedChoiceResponse.choiceResources: List<ChoiceResourceEntity>
    get() =
        resources.map { resource ->
            ChoiceResourceEntity(
                resourceId = resource.id,
                choiceId = id,
                changeValue = resource.changeValue
            )
        }