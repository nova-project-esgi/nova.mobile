package com.esgi.nova.dto

data class CompleteEventsDTO(
    val events: List<EventDTO>,
    val eventBackgroundUrls: List<EventUrlDTO>
)