package com.esgi.nova.resources.infrastructure.data

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class ResourceDAO : BaseDao<UUID, ResourceEntity> {
    @Query("SELECT * FROM resources WHERE id IN (:ids)")
    abstract override suspend fun loadAllByIds(ids: List<UUID>): List<ResourceEntity>


    @Delete
    abstract override suspend fun delete(entity: ResourceEntity)

    @Query("SELECT * FROM resources")
    abstract override suspend fun getAll(): List<ResourceEntity>


    @Query("SELECT * FROM resources WHERE id = :id")
    abstract override suspend fun getById(id: UUID): List<ResourceEntity>

    @Query("DELETE FROM resources")
    abstract override suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(vararg entities: ResourceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(entities: Collection<ResourceEntity>): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertOne(entity: ResourceEntity): Unit
}