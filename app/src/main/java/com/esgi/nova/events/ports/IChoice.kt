package com.esgi.nova.events.ports

import java.util.*

interface IChoice {
    val id: UUID
    val eventId: UUID
    val title: String
    val description: String
}

