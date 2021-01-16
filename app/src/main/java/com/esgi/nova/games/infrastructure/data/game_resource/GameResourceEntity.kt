package com.esgi.nova.games.infrastructure.data.game_resource

import androidx.room.*
import com.esgi.nova.games.infrastructure.data.game.GameEntity
import com.esgi.nova.games.ports.IGameResource
import com.esgi.nova.infrastructure.data.IIdEntity
import com.esgi.nova.infrastructure.data.UUIDConverter
import com.esgi.nova.resources.infrastructure.data.ResourceEntity
import java.util.*

@Entity(
    tableName = "game_resource",
    indices = [
        Index("resource_id"),
        Index("game_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = ResourceEntity::class,
            parentColumns = ["id"],
            childColumns = ["resource_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["game_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ], primaryKeys = ["resource_id", "game_id"]
)
data class GameResourceEntity(
    @ColumnInfo(name = "resource_id")
    @field:TypeConverters(UUIDConverter::class)
    override var resourceId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "game_id")
    @field:TypeConverters(UUIDConverter::class)
    override var gameId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "total")
    override var total: Int
): IGameResource{

}

