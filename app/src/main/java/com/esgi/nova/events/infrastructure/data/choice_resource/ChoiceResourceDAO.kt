package com.esgi.nova.events.infrastructure.data.choice_resource

import androidx.room.*
import com.esgi.nova.events.infrastructure.data.choices.ChoiceEntity
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class ChoiceResourceDAO : BaseDao<UUID, ChoiceResourceEntity>() {

    @Query("SELECT * FROM choice_resource")
    abstract override fun getAll(): List<ChoiceResourceEntity>

    @Query("DELETE FROM choice_resource")
    abstract override fun deleteAll()

    @Query("SELECT * FROM choice_resource WHERE choice_id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<ChoiceResourceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: ChoiceResourceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(entities: Collection<ChoiceResourceEntity>)

    @Delete
    abstract override fun delete(entity: ChoiceResourceEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertOne(entity: ChoiceResourceEntity)


    @Query("SELECT * FROM choice_resource")
    @Transaction
    abstract fun getAllChoiceWithResource(): List<ChoiceWithResource>
}