package com.esgi.nova.difficulties.infrastructure.api.responses

import com.esgi.nova.difficulties.infrastructure.api.models.DifficultyResource
import com.esgi.nova.difficulties.infrastructure.api.models.ResumedDifficulty
import com.esgi.nova.difficulties.ports.IDifficulty
import java.util.*

data class TranslatedDifficultyResponse(
    override val id: UUID,
    val language: String,
    override val name: String,
    val resources: List<DifficultyResourceResponse>, override val rank: Int
) : IDifficulty {

    fun toDifficultyWithResourceResumes() =
        ResumedDifficulty(
            id = id,
            resources = resources.map {
                DifficultyResource(
                    resourceId = it.resourceId,
                    difficultyId = id,
                    startValue = it.startValue
                )
            }.toMutableList(),
            name = name,
            rank = rank
        )
}

