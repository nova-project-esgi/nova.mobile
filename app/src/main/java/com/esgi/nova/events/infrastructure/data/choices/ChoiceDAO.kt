package com.esgi.nova.events.infrastructure.data.choices

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class ChoiceDAO : BaseDao<UUID, ChoiceEntity>() {
    @Query("SELECT * FROM choices")
    abstract override fun getAll(): List<ChoiceEntity>

    @Query("DELETE FROM choices")
    abstract override fun deleteAll()

    @Query("SELECT * FROM choices WHERE id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<ChoiceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: ChoiceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(entities: Collection<ChoiceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertOne(entity: ChoiceEntity)

    @Delete
    abstract override fun delete(entity: ChoiceEntity)
}