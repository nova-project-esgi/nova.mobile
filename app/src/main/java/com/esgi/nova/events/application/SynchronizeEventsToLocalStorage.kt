package com.esgi.nova.events.application

import com.esgi.nova.events.infrastructure.api.EventApiRepository
import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceDbRepository
import com.esgi.nova.events.infrastructure.data.choices.ChoiceDbRepository
import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import javax.inject.Inject

class SynchronizeEventsToLocalStorage @Inject constructor(
    private val eventDbRepository: EventDbRepository,
    private val choiceDbRepository: ChoiceDbRepository,
    private val eventApiRepository: EventApiRepository,
    private val choiceResourceDbRepository: ChoiceResourceDbRepository
) {

    fun execute(language: String) {
        val translatedEventsWrappers = eventApiRepository.getAllTranslatedEvents(language)

        eventDbRepository.insertAll(translatedEventsWrappers.map { it.data })

        translatedEventsWrappers.forEach { translatedEvent ->
            choiceDbRepository.insertAll(translatedEvent.data.choices)
            translatedEvent.data.choices.forEach { translatedChoice ->
                choiceResourceDbRepository.insertAll(translatedChoice.resources)
            }
        }
        val eventT = eventDbRepository.getAllEventWithChoices()
        val choiceT = choiceResourceDbRepository.getAllChoiceWithResource()

    }
}


