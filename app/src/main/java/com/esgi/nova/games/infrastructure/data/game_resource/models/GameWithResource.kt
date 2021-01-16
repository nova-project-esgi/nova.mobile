package com.esgi.nova.games.infrastructure.data.game_resource.models

import androidx.room.Embedded
import androidx.room.Relation
import com.esgi.nova.difficulties.infrastructure.data.difficulty.DifficultyEntity
import com.esgi.nova.games.infrastructure.data.game.GameEntity
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceEntity
import com.esgi.nova.resources.infrastructure.data.ResourceEntity

class GameWithResource(

) {
    @Embedded
    lateinit var gameResource: GameResourceEntity

    @Relation(
        entity = GameEntity::class,
        parentColumn = "game_id",
        entityColumn = "id"
    )
    lateinit var gameSet: Set<GameEntity>

    @Relation(
        entity = ResourceEntity::class,
        parentColumn = "resource_id",
        entityColumn = "id"
    )
    lateinit var resourceSet: Set<ResourceEntity>

    val resource: ResourceEntity get() = resourceSet.first()
    val game: GameEntity get() = gameSet.first()

    fun toTotalValueResource() =
        TotalValueResource(
            id = resource.id,
            name = resource.name,
            total = gameResource.total
        )

    fun toGameResourceEdition() =
        GameResourceEdition(
            resourceId = resource.id,
            total = gameResource.total
        )
}