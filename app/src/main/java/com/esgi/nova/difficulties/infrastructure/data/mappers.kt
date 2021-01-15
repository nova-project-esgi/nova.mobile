package com.esgi.nova.difficulties.infrastructure.data

import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.models.DifficultyWithResource
import com.esgi.nova.difficulties.ports.IDetailedDifficulty

fun List<DifficultyWithResource>.toDetailedDifficulties(): List<IDetailedDifficulty> {
    val difficultyWithResourcesList = mutableListOf<IDetailedDifficulty>()
    this.forEach { difficultyWithResource ->
        difficultyWithResourcesList
            .firstOrNull { difficultyWithResources -> difficultyWithResource.difficulty.id == difficultyWithResources.id }
            ?.let { difficultyWithResources ->
                difficultyWithResources.resources += difficultyWithResource.toStartValueResource()
                return@forEach
            }
        difficultyWithResourcesList += difficultyWithResource.toDifficultyWithResources()
    }
    return difficultyWithResourcesList
}