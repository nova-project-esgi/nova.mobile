package com.esgi.nova.events.infrastructure.data

import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceWithResource
import com.esgi.nova.events.infrastructure.data.choices.models.DetailedChoice
import com.esgi.nova.events.ports.IDetailedChoice

fun List<ChoiceWithResource>.toDetailedChoices(): MutableList<out IDetailedChoice> {
    val detailedChoicesList = mutableListOf<DetailedChoice>()
    this.forEach { choiceWithResource ->
        detailedChoicesList
            .firstOrNull { detailedChoice -> choiceWithResource.choice.id == detailedChoice.id }
            ?.let { detailedChoice ->
                detailedChoice.resources += choiceWithResource.toChangeValueResource()
                return@forEach
            }
        detailedChoicesList += choiceWithResource.toDetailedChoice()
    }
    return detailedChoicesList
}