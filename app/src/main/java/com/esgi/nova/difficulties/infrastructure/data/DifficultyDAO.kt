package com.esgi.nova.difficulties.infrastructure.data

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class DifficultyDAO : BaseDao<UUID, Difficulty>() {

    @Query("SELECT * FROM difficulties")
    abstract override fun getAll(): List<Difficulty>

    @Query("DELETE FROM difficulties")
    abstract override fun deleteAll()

    @Query("SELECT * FROM difficulties WHERE id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<Difficulty>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: Difficulty)

    @Delete
    abstract override fun delete(entity: Difficulty)
}

