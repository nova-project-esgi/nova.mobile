package com.esgi.nova.games.infrastructure.data.game_event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class GameEventDao : BaseDao<UUID, GameEventEntity>() {
    @Query("SELECT * FROM game_event")
    abstract override fun getAll(): List<GameEventEntity>

    @Query("DELETE FROM game_event")
    abstract override fun deleteAll()

    @Query("SELECT * FROM game_event WHERE game_id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<GameEventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: GameEventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(entities: Collection<GameEventEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertOne(entity: GameEventEntity)
}