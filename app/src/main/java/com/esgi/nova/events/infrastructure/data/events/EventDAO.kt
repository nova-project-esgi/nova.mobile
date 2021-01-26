package com.esgi.nova.events.infrastructure.data.events

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class EventDAO : BaseDao<UUID, EventEntity> {
    @Query("SELECT * FROM events")
    abstract override suspend fun getAll(): List<EventEntity>

    @Query("SELECT * FROM events WHERE isDaily = :isDaily")
    abstract fun getAllByIsDaily(isDaily: Boolean): List<EventEntity>

    @Query("SELECT * FROM events WHERE id = :id")
    abstract override suspend fun getById(id: UUID): List<EventEntity>

    @Query("DELETE FROM events")
    abstract override suspend fun deleteAll()

    @Query("SELECT * FROM events WHERE id IN (:ids)")
    abstract override suspend fun loadAllByIds(ids: List<UUID>): List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(vararg entities: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertAll(entities: Collection<EventEntity>): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertOne(entity: EventEntity): Unit

    @Delete
    abstract override suspend fun delete(entity: EventEntity)

    @Transaction
    @Query("SELECT * FROM events")
    abstract suspend fun getAllEventWithChoices(): List<EventWithChoices>

    @Transaction
    @Query("SELECT * FROM events WHERE id = :eventId")
    abstract suspend fun getAllEventWithChoicesByEventId(eventId: UUID): List<EventWithChoices>


}

