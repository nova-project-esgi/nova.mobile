package com.esgi.nova.events.infrastructure.dto

import java.util.*

data class TranslatedChoiceDto(
    val id: UUID,
    val eventId: UUID,
    val resources: List<TranslatedChoiceResourceDto>,
    val title: String,
    val description: String,
    val language: String
) {
}