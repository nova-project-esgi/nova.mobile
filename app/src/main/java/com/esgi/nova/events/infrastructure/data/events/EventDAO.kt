package com.esgi.nova.events.infrastructure.data.events

import androidx.room.*
import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceEntity
import com.esgi.nova.events.infrastructure.data.choices.ChoiceEntity
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class EventDAO : BaseDao<UUID, EventEntity> (){
    @Query("SELECT * FROM events")
    abstract override fun getAll(): List<EventEntity>

    @Query("DELETE FROM events")
    abstract override fun deleteAll()

    @Query("SELECT * FROM events WHERE id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: EventEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(entities: Collection<EventEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertOne(entity: EventEntity)
    @Delete
    abstract override fun delete(entity: EventEntity)

    @Transaction
    @Query("SELECT * FROM events")
    abstract  fun getAllEventWithChoices(): List<EventWithChoices>
}
