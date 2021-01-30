package com.esgi.nova.events.domain.models

import java.util.*

data class Event(
    val id: UUID = UUID.randomUUID(),
    val description: String,
    val title: String
)