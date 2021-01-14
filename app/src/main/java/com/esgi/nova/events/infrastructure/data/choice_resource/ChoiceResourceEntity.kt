package com.esgi.nova.events.infrastructure.data.choice_resource

import androidx.room.*
import com.esgi.nova.events.infrastructure.data.choices.ChoiceEntity
import com.esgi.nova.events.ports.IChoiceResource
import com.esgi.nova.infrastructure.data.UUIDConverter
import com.esgi.nova.resources.infrastructure.data.ResourceEntity
import java.util.*


@Entity(
    tableName = "choice_resource",
    indices = [
        Index("resource_id"),
        Index("choice_id")
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
            entity = ChoiceEntity::class,
            parentColumns = ["id"],
            childColumns = ["choice_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ], primaryKeys = ["resource_id", "choice_id"]
)
data class ChoiceResourceEntity(
    @ColumnInfo(name="resource_id")
    @field:TypeConverters(UUIDConverter::class)
    override val resourceId: UUID = UUID.randomUUID(),
    @ColumnInfo(name="choice_id")
    @field:TypeConverters(UUIDConverter::class)
    override val choiceId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "change_value")
    override val changeValue: Int
): IChoiceResource {
}
