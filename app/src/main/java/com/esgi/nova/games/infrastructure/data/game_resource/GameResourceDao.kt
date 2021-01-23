package com.esgi.nova.games.infrastructure.data.game_resource

import androidx.room.*
import com.esgi.nova.games.infrastructure.data.game_resource.models.GameWithResource
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class GameResourceDao : BaseDao<UUID, GameResourceEntity>() {
    @Query("SELECT * FROM game_resource")
    abstract override fun getAll(): List<GameResourceEntity>

    @Query("SELECT * FROM game_resource WHERE game_id = :id")
    abstract override fun getById(id: UUID): List<GameResourceEntity>


    @Query("DELETE FROM game_resource")
    abstract override fun deleteAll()

    @Query("SELECT * FROM game_resource WHERE game_id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<GameResourceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: GameResourceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(entities: Collection<GameResourceEntity>): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertOne(entity: GameResourceEntity): Unit

    @Query("SELECT * FROM game_resource WHERE game_id = :gameId")
    @Transaction
    abstract fun getAllGameWithResourceById(gameId: UUID): List<GameWithResource>

    @Query("SELECT * FROM game_resource WHERE game_id = :gameId AND  resource_id = :resourceId ")
    abstract fun getByGameAndResourceId(gameId: UUID, resourceId: UUID): List<GameResourceEntity>
}