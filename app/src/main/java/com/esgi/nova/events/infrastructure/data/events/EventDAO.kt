package com.esgi.nova.events.infrastructure.data.events

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class EventDAO : BaseDao<UUID, EventEntity> (){
    @Query("SELECT * FROM events")
    abstract override fun getAll(): List<EventEntity>

    @Query("SELECT * FROM events WHERE id = :id")
    abstract override fun getById(id: UUID): List<EventEntity>

    @Query("DELETE FROM events")
    abstract override fun deleteAll()

    @Query("SELECT * FROM events WHERE id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: EventEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(entities: Collection<EventEntity>): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertOne(entity: EventEntity): Unit

    @Delete
    abstract override fun delete(entity: EventEntity)

    @Transaction
    @Query("SELECT * FROM events")
    abstract  fun getAllEventWithChoices(): List<EventWithChoices>

    @Transaction
    @Query("SELECT * FROM events WHERE id = :eventId")
    abstract  fun getAllEventWithChoicesByEventId(eventId: UUID): List<EventWithChoices>


}

