package com.esgi.nova.games.infrastructure.data.game

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class GameDao : BaseDao<UUID, GameEntity>() {
    @Query("SELECT * FROM games")
    abstract override fun getAll(): List<GameEntity>

    @Query("SELECT * FROM games WHERE id = :id")
    abstract override fun getById(id: UUID): List<GameEntity>

    @Query("SELECT * FROM games WHERE isEnded = :isEnded AND user_id = :userId")
    abstract fun getByIsEndedAndUserId(isEnded: Boolean, userId: UUID): List<GameEntity>

    @Query("DELETE FROM games")
    abstract override fun deleteAll()

    @Query("SELECT * FROM games WHERE id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<GameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: GameEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(entities: Collection<GameEntity>): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertOne(entity: GameEntity): Unit

    @Delete
    abstract override fun delete(entity: GameEntity)
}