package com.esgi.nova.events.infrastructure.dto

import java.util.*


data class TranslatedChoiceResourceDto(
    val id: UUID,
    val language: String,
    val name: String,
    val changeValue: Int
) {

}