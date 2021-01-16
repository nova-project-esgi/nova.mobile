package com.esgi.nova.games.infrastructure.data.game_event

import androidx.room.*
import com.esgi.nova.events.infrastructure.data.events.EventEntity
import com.esgi.nova.games.infrastructure.data.game.GameEntity
import com.esgi.nova.games.ports.IGameEvent
import com.esgi.nova.infrastructure.data.DateConverter
import com.esgi.nova.infrastructure.data.IIdEntity
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.time.LocalDateTime
import java.util.*

@Entity(
    tableName = "game_event",
    indices = [
        Index("event_id"),
        Index("game_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = EventEntity::class,
            parentColumns = ["id"],
            childColumns = ["event_id"],
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
    ], primaryKeys = ["event_id", "game_id"]
)
data class GameEventEntity(
    @ColumnInfo(name = "event_id")
    @field:TypeConverters(UUIDConverter::class)
    override val eventId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "game_id")
    @field:TypeConverters(UUIDConverter::class)
    override val gameId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "link_time")
    @field:TypeConverters(DateConverter::class)
    override val linkTime: LocalDateTime
): IGameEvent {

}

