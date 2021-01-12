package com.esgi.nova.difficulties.infrastructure.data

import androidx.room.*
import com.esgi.nova.infrastructure.data.UUIDConverter
import com.esgi.nova.resources.infrastructure.data.Resource
import java.util.*


@Entity(
    tableName = "difficulty_resource",
    indices = [
        Index("resource_id"),
        Index("difficulty_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = Resource::class,
            parentColumns = ["id"],
            childColumns = ["resource_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Difficulty::class,
            parentColumns = ["id"],
            childColumns = ["difficulty_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ], primaryKeys = ["resource_id", "difficulty_id"]
)
data class DifficultyResource(
    @ColumnInfo(name="resource_id")
    @field:TypeConverters(UUIDConverter::class)
    val resourceId: UUID = UUID.randomUUID(),
    @ColumnInfo(name="difficulty_id")
    @field:TypeConverters(UUIDConverter::class)
    val difficultyId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "start_value")
    val startValue: Int
) {
}
