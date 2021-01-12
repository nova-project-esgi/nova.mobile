package com.esgi.nova.events.infrastructure.data.choices

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class ChoiceDAO : BaseDao<UUID, Choice>() {
    @Query("SELECT * FROM choices")
    abstract override fun getAll(): List<Choice>

    @Query("DELETE FROM choices")
    abstract override fun deleteAll()

    @Query("SELECT * FROM choices WHERE id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<Choice>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: Choice)

    @Delete
    abstract override fun delete(entity: Choice)
}