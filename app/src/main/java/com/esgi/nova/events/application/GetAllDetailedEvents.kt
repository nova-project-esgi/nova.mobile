package com.esgi.nova.events.application

import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import com.esgi.nova.events.ports.IDetailedEvent
import javax.inject.Inject

class GetAllDetailedEvents @Inject constructor(
    private val eventDbRepository: EventDbRepository
) {

    fun execute(): List<IDetailedEvent>  = eventDbRepository.getAllDetailedEvent()
}