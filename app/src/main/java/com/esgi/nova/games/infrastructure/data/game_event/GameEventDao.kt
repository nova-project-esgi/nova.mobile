package com.esgi.nova.games.infrastructure.data.game_event

import androidx.room.*
import com.esgi.nova.games.infrastructure.data.game_event.models.GameWithEvent
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class GameEventDao : BaseDao<UUID, GameEventEntity> {
    @Query("SELECT * FROM game_event")
    abstract override suspend fun getAll(): List<GameEventEntity>

    @Query("SELECT * FROM game_event WHERE game_id = :id")
    abstract override suspend fun getById(id: UUID): List<GameEventEntity>

    @Query("SELECT * FROM game_event WHERE event_id = :id")
    abstract fun getAllByEventId(id: UUID): List<GameEventEntity>

    @Query("DELETE FROM game_event")
    abstract override suspend fun deleteAll()

    @Query("SELECT * FROM game_event WHERE game_id IN (:ids)")
    abstract override suspend fun loadAllByIds(ids: List<UUID>): List<GameEventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(vararg entities: GameEventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(entities: Collection<GameEventEntity>): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertOne(entity: GameEventEntity): Unit


    @Query("SELECT * FROM game_event WHERE game_id = :gameId")
    @Transaction
    abstract suspend fun getAllGameWithEventById(gameId: UUID): List<GameWithEvent>

    @Query("SELECT COUNT(*) FROM game_event WHERE game_id = :gameId")
    @Transaction
    abstract suspend fun getEventsCountByGame(gameId: UUID): Int
}