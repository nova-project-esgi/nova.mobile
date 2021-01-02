package com.esgi.nova.dto

import java.util.*

data class EventDTO(
    val id: UUID,
    val title: String,
    val description: String,
    val language: String,
    val choices: List<ChoiceDTO>
)