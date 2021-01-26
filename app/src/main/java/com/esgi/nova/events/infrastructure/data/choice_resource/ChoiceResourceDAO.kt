package com.esgi.nova.events.infrastructure.data.choice_resource

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class ChoiceResourceDAO : BaseDao<UUID, ChoiceResourceEntity> {

    @Query("SELECT * FROM choice_resource")
    abstract override suspend fun getAll(): List<ChoiceResourceEntity>

    @Query("DELETE FROM choice_resource")
    abstract override suspend fun deleteAll()

    @Query("SELECT * FROM choice_resource WHERE choice_id = :id")
    abstract override suspend fun getById(id: UUID): List<ChoiceResourceEntity>

    @Query("SELECT * FROM choice_resource WHERE choice_id IN (:ids)")
    abstract override suspend fun loadAllByIds(ids: List<UUID>): List<ChoiceResourceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(vararg entities: ChoiceResourceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(entities: Collection<ChoiceResourceEntity>): Unit

    @Delete
    abstract override suspend fun delete(entity: ChoiceResourceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertOne(entity: ChoiceResourceEntity): Unit


    @Query("SELECT * FROM choice_resource")
    @Transaction
    abstract suspend fun getAllChoiceWithResource(): List<ChoiceWithResource>

    @Query("SELECT * FROM choice_resource WHERE choice_id = :id")
    @Transaction
    abstract suspend fun getAllChoiceWithResourceByChoiceId(id: UUID): List<ChoiceWithResource>
}