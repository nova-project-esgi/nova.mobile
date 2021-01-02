package com.esgi.nova.dto

import java.util.*

data class ChoiceDTO(
    val id: UUID,
    val eventId: UUID,
    val resources: List<ChoiceResourcesDTO>,
    val title: String,
    val description: String,
    val language: String
)