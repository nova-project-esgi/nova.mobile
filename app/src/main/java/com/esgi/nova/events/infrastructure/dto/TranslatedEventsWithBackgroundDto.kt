package com.esgi.nova.events.infrastructure.dto

import java.util.*

data class TranslatedEventsWithBackgroundDto(
    val id: UUID,
    val title: String,
    val description: String,
    val language: String,
    val choices: List<TranslatedChoiceView>,
    val backgroundUrl: Link
)
