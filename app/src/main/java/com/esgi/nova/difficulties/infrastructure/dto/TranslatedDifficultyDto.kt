package com.esgi.nova.difficulties.infrastructure.dto

import java.util.*

data class TranslatedDifficultyDto(
    val id: UUID,
    val language: String,
    val name: String,
    val resources: List<DifficultyResourceDto>
) {

}

