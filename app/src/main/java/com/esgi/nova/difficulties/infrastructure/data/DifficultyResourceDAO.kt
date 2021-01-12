package com.esgi.nova.difficulties.infrastructure.data

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class DifficultyResourceDAO : BaseDao<UUID, DifficultyResource>() {

    @Query("SELECT * FROM difficulty_resource")
    abstract override fun getAll(): List<DifficultyResource>

    @Query("DELETE FROM difficulty_resource")
    abstract override fun deleteAll()

    @Query("SELECT * FROM difficulty_resource WHERE difficulty_id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<DifficultyResource>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: DifficultyResource)

    @Delete
    abstract override fun delete(entity: DifficultyResource)

    @Query("SELECT * FROM difficulty_resource")
    abstract fun getAllDifficultyWithResource(): List<DifficultyWithResource>
}