package com.esgi.nova.difficulties.infrastructure.data.difficulty

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class DifficultyDAO : BaseDao<UUID, DifficultyEntity>() {

    @Query("SELECT * FROM difficulties")
    abstract override fun getAll(): List<DifficultyEntity>

    @Query("SELECT * FROM difficulties WHERE id = :id")
    abstract override fun getById(id: UUID): List<DifficultyEntity>

    @Query("DELETE FROM difficulties")
    abstract override fun deleteAll()

    @Query("SELECT * FROM difficulties WHERE id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<DifficultyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: DifficultyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(entities: Collection<DifficultyEntity>): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertOne(entity: DifficultyEntity): Unit

    @Delete
    abstract override fun delete(entity: DifficultyEntity)

}

