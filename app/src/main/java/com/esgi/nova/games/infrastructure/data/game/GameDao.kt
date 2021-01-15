package com.esgi.nova.games.infrastructure.data.game

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class GameDao : BaseDao<UUID, GameEntity>() {
    @Query("SELECT * FROM games")
    abstract override fun getAll(): List<GameEntity>

    @Query("DELETE FROM games")
    abstract override fun deleteAll()

    @Query("SELECT * FROM games WHERE id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<GameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: GameEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(entities: Collection<GameEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertOne(entity: GameEntity)

    @Delete
    abstract override fun delete(entity: GameEntity)
}