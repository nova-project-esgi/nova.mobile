package com.esgi.nova.difficulties.infrastructure.data.difficulty_resource

import androidx.room.*
import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.models.DifficultyWithResource
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class DifficultyResourceDAO : BaseDao<UUID, DifficultyResourceEntity> {

    @Query("SELECT * FROM difficulty_resource")
    abstract override suspend fun getAll(): List<DifficultyResourceEntity>

    @Query("SELECT * FROM difficulty_resource WHERE difficulty_id = :id")
    abstract override suspend fun getById(id: UUID): List<DifficultyResourceEntity>


    @Query("DELETE FROM difficulty_resource")
    abstract override suspend fun deleteAll()

    @Query("SELECT * FROM difficulty_resource WHERE difficulty_id IN (:ids)")
    abstract override suspend fun loadAllByIds(ids: List<UUID>): List<DifficultyResourceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(vararg entities: DifficultyResourceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(entities: Collection<DifficultyResourceEntity>): Unit

    @Delete
    abstract override suspend fun delete(entity: DifficultyResourceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertOne(entity: DifficultyResourceEntity): Unit

    @Query("SELECT * FROM difficulty_resource")
    @Transaction
    abstract suspend fun getAllDifficultyWithResource(): List<DifficultyWithResource>

    @Query("SELECT * FROM difficulty_resource WHERE difficulty_id = :id")
    @Transaction
    abstract suspend fun getAllDifficultyWithResourceById(id: UUID): List<DifficultyWithResource>
}