package com.esgi.nova.difficulties

import com.esgi.nova.difficulties.infrastructure.data.DifficultyResource
import com.esgi.nova.difficulties.infrastructure.dto.TranslatedDifficultyDto

val TranslatedDifficultyDto.difficultyResources: List<DifficultyResource>
    get() =
        resources.map {
            DifficultyResource(
                resourceId = it.resourceId,
                difficultyId = id,
                startValue = it.startValue
            )
        }