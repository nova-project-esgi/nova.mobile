package com.esgi.nova.events.infrastructure.data.choices

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class ChoiceDAO : BaseDao<UUID, ChoiceEntity> {
    @Query("SELECT * FROM choices")
    abstract override suspend fun getAll(): List<ChoiceEntity>

    @Query("SELECT * FROM choices WHERE id = :id")
    abstract override suspend fun getById(id: UUID): List<ChoiceEntity>

    @Query("DELETE FROM choices")
    abstract override suspend fun deleteAll()

    @Query("SELECT * FROM choices WHERE id IN (:ids)")
    abstract override suspend fun loadAllByIds(ids: List<UUID>): List<ChoiceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(vararg entities: ChoiceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(entities: Collection<ChoiceEntity>): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertOne(entity: ChoiceEntity): Unit

    @Delete
    abstract override suspend fun delete(entity: ChoiceEntity)
}