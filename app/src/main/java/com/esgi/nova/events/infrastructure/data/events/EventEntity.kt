package com.esgi.nova.events.infrastructure.data.events

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceWithResource
import com.esgi.nova.events.infrastructure.data.events.models.DetailedEvent
import com.esgi.nova.events.infrastructure.data.toDetailedChoices
import com.esgi.nova.events.ports.IEvent
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey()
    @field:TypeConverters(UUIDConverter::class)
    override var id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "description") override var description: String,
    @ColumnInfo(name = "title") override var title: String
) : IEvent {
    fun toDetailedEvent(choices: List<ChoiceWithResource>) = DetailedEvent(
        choices = choices.toDetailedChoices(),
        description = description,
        title = title,
        id = id
    )
}