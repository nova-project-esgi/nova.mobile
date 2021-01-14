package com.esgi.nova.difficulties

import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceEntity
import com.esgi.nova.difficulties.infrastructure.api.responses.TranslatedDifficultyResponse

val TranslatedDifficultyResponse.difficultyResources: List<DifficultyResourceEntity>
    get() =
        resources.map {
            DifficultyResourceEntity(
                resourceId = it.resourceId,
                difficultyId = id,
                startValue = it.startValue
            )
        }
