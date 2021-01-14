package com.esgi.nova.difficulties.infrastructure.data.difficulty

import androidx.room.*
import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceEntity
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class DifficultyDAO : BaseDao<UUID, DifficultyEntity>() {

    @Query("SELECT * FROM difficulties")
    abstract override fun getAll(): List<DifficultyEntity>

    @Query("DELETE FROM difficulties")
    abstract override fun deleteAll()

    @Query("SELECT * FROM difficulties WHERE id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<DifficultyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: DifficultyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(entities: Collection<DifficultyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertOne(entity: DifficultyEntity)
    @Delete
    abstract override fun delete(entity: DifficultyEntity)

}

