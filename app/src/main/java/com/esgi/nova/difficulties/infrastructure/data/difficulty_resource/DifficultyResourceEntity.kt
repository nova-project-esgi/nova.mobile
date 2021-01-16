package com.esgi.nova.difficulties.infrastructure.data.difficulty_resource

import androidx.room.*
import com.esgi.nova.difficulties.infrastructure.data.difficulty.DifficultyEntity
import com.esgi.nova.difficulties.ports.IDifficultyResource
import com.esgi.nova.infrastructure.data.IIdEntity
import com.esgi.nova.infrastructure.data.UUIDConverter
import com.esgi.nova.resources.infrastructure.data.ResourceEntity
import java.util.*


@Entity(
    tableName = "difficulty_resource",
    indices = [
        Index("resource_id"),
        Index("difficulty_id")
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
            entity = DifficultyEntity::class,
            parentColumns = ["id"],
            childColumns = ["difficulty_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ], primaryKeys = ["resource_id", "difficulty_id"]
)
data class DifficultyResourceEntity(
    @ColumnInfo(name="resource_id")
    @field:TypeConverters(UUIDConverter::class)
    override val resourceId: UUID = UUID.randomUUID(),
    @ColumnInfo(name="difficulty_id")
    @field:TypeConverters(UUIDConverter::class)
    override val difficultyId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "start_value")
    override val startValue: Int
): IDifficultyResource {

}
