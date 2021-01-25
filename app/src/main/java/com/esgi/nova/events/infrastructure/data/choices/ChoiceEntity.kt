package com.esgi.nova.events.infrastructure.data.choices

import androidx.room.*
import com.esgi.nova.events.infrastructure.data.events.EventEntity
import com.esgi.nova.events.ports.IChoice
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

@Entity(
    tableName = "choices",
    indices = [
        Index("event_id")
    ],
    foreignKeys = [ForeignKey(
        entity = EventEntity::class,
        childColumns = ["event_id"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
)])
data class ChoiceEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    @field:TypeConverters(UUIDConverter::class)
    override var id: UUID = UUID.randomUUID(),
    @field:TypeConverters(UUIDConverter::class)
    @ColumnInfo(name = "event_id")
    override var eventId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "title") override val title: String,
    @ColumnInfo(name = "description") override val description: String
): IChoice

