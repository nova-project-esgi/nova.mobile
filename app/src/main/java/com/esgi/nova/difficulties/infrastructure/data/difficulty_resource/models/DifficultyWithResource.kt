package com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.models

import androidx.room.Embedded
import androidx.room.Relation
import com.esgi.nova.difficulties.infrastructure.data.difficulty.DifficultyEntity
import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceEntity
import com.esgi.nova.resources.infrastructure.data.ResourceEntity

class DifficultyWithResource {
    @Embedded
    lateinit var difficultyResource: DifficultyResourceEntity

    @Relation(
        entity = DifficultyEntity::class,
        parentColumn = "difficulty_id",
        entityColumn = "id"
    )
    lateinit var difficultySet: Set<DifficultyEntity>

    @Relation(
        entity = ResourceEntity::class,
        parentColumn = "resource_id",
        entityColumn = "id"
    )
    lateinit var resourceSet: Set<ResourceEntity>

    val resource: ResourceEntity get() = resourceSet.first()
    val difficulty: DifficultyEntity get() = difficultySet.first()

    fun toStartValueResource() =
        StartValueResource(
            id = resource.id,
            name = resource.name,
            startValue = difficultyResource.startValue
        )
    fun toDifficultyWithResources() =
        DetailedDifficulty(
            id = difficulty.id,
            name = difficulty.name,
            resources = mutableListOf(toStartValueResource()),
            rank = difficulty.rank
        )
}