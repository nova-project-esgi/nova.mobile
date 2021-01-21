package com.esgi.nova.games.infrastructure.data.game

import androidx.room.*
import com.esgi.nova.difficulties.infrastructure.data.difficulty.DifficultyEntity
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.infrastructure.data.IIdEntity
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

@Entity(
    tableName = "games",
    indices = [
        Index("difficulty_id")
    ],
    foreignKeys = [ForeignKey(
        entity = DifficultyEntity::class,
        childColumns = ["difficulty_id"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class GameEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:TypeConverters(UUIDConverter::class)
    override var id: UUID = UUID.randomUUID(),
    @field:TypeConverters(UUIDConverter::class)
    @ColumnInfo(name = "difficulty_id")
    override var difficultyId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "user_id")
    override var userId: UUID,
    @ColumnInfo(name = "duration")
    override var duration: Int = 0,
    @ColumnInfo(name = "is_ended")
    override var isEnded: Boolean = false
) : IGame{}

