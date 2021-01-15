package com.esgi.nova.events.infrastructure.data.events

import androidx.room.Embedded
import androidx.room.Relation
import com.esgi.nova.events.infrastructure.data.choices.ChoiceEntity

data class EventWithChoices(
    @Embedded
    val event: EventEntity
){
    @Relation(
        parentColumn = "id",
        entityColumn = "event_id"
    )
    lateinit var choices: Set<ChoiceEntity>

}

