package com.esgi.nova.events.infrastructure.dto

import java.util.*

data class TranslatedChoiceView(
    val id: UUID,
    val eventId: UUID,
    val resources: List<TranslatedChoiceResourceView>,
    val title: String,
    val description: String,
    val language: String
) {
}