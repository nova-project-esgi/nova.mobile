package com.esgi.nova.difficulties.infrastructure.data

import androidx.room.Embedded
import androidx.room.Relation
import com.esgi.nova.resources.infrastructure.data.Resource

class DifficultyWithResource(

) {
    @Embedded
    lateinit var difficultyResource: DifficultyResource

    @Relation(
        entity = Difficulty::class,
        parentColumn = "difficulty_id",
        entityColumn = "id"
    )
    lateinit var difficulty: Set<Difficulty>

    @Relation(
        entity = Resource::class,
        parentColumn = "resource_id",
        entityColumn = "id"
    )
    lateinit var resource: Set<Resource>

}