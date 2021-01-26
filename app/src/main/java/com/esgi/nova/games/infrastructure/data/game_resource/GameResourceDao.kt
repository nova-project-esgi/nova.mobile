package com.esgi.nova.games.infrastructure.data.game_resource

import androidx.room.*
import com.esgi.nova.games.infrastructure.data.game_resource.models.GameWithResource
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class GameResourceDao : BaseDao<UUID, GameResourceEntity> {
    @Query("SELECT * FROM game_resource")
    abstract override suspend fun getAll(): List<GameResourceEntity>

    @Query("SELECT * FROM game_resource WHERE game_id = :id")
    abstract override suspend fun getById(id: UUID): List<GameResourceEntity>


    @Query("DELETE FROM game_resource")
    abstract override suspend fun deleteAll()

    @Query("SELECT * FROM game_resource WHERE game_id IN (:ids)")
    abstract override suspend fun loadAllByIds(ids: List<UUID>): List<GameResourceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(vararg entities: GameResourceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(entities: Collection<GameResourceEntity>): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertOne(entity: GameResourceEntity): Unit

    @Query("SELECT * FROM game_resource WHERE game_id = :gameId")
    @Transaction
    abstract suspend fun getAllGameWithResourceById(gameId: UUID): List<GameWithResource>

    @Query("SELECT * FROM game_resource WHERE game_id = :gameId AND  resource_id = :resourceId ")
    abstract suspend fun getByGameAndResourceId(
        gameId: UUID,
        resourceId: UUID
    ): List<GameResourceEntity>
}