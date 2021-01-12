package com.esgi.nova.events.application

import com.esgi.nova.events.infrastructure.api.EventApiRepository
import com.esgi.nova.events.infrastructure.data.choices.ChoiceDbRepository
import com.esgi.nova.events.infrastructure.data.events.Event
import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import com.esgi.nova.events.infrastructure.dto.TranslatedEventsWithBackgroundDto
import com.esgi.nova.utils.reflectMapCollection
import javax.inject.Inject

class SynchronizeEventsToLocalStorage @Inject constructor(
    private val eventDbRepository: EventDbRepository,
    private val choiceDbRepository: ChoiceDbRepository,
    private val eventApiRepository: EventApiRepository
) {

    fun execute() {
        val events = eventApiRepository.getAllTranslatedEvents("en")
            .reflectMapCollection<TranslatedEventsWithBackgroundDto, Event>()
            .toTypedArray()
        println(events);
    }
}


