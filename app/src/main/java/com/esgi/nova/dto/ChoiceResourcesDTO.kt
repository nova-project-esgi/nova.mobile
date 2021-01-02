package com.esgi.nova.dto

import java.util.*

data class ChoiceResourcesDTO(
    val id: UUID,
    val language: String,
    val name: String,
    val changeValue: Int
)
