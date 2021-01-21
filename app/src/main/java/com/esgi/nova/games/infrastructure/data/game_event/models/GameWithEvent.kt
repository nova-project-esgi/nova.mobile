package com.esgi.nova.games.infrastructure.data.game_event.models

import androidx.room.Embedded
import androidx.room.Relation
import com.esgi.nova.events.infrastructure.data.events.EventEntity
import com.esgi.nova.games.infrastructure.data.game.GameEntity
import com.esgi.nova.games.infrastructure.data.game_event.GameEventEntity

class GameWithEvent(

) {
    @Embedded
    lateinit var gameEvent: GameEventEntity

    @Relation(
        entity = GameEntity::class,
        parentColumn = "game_id",
        entityColumn = "id"
    )
    lateinit var gameSet: Set<GameEntity>

    @Relation(
        entity = EventEntity::class,
        parentColumn = "event_id",
        entityColumn = "id"
    )
    lateinit var eventSet: Set<EventEntity>

    val event: EventEntity get() = eventSet.first()
    val game: GameEntity get() = gameSet.first()

    fun toLinkTimeEvent() =
        LinkTimeEvent(
            id = event.id,
            title = event.title,
            description = event.description,
            linkTime = gameEvent.linkTime
        )

    fun toGameEventEdition() =
        GameEventEdition(
            eventId = event.id,
            linkTime = gameEvent.linkTime
        )



}