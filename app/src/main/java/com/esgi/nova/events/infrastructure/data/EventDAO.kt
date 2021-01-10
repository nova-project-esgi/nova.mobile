package com.esgi.nova.events.infrastructure.data

import androidx.room.*

@Dao
interface EventDAO {
    @Query("SELECT * FROM event")
    fun getAll(): List<Event>

    @Query("DELETE FROM event")
    fun deleteAll()

    @Query("SELECT * FROM event WHERE id IN (:eventIds)")
    fun loadAllByIds(eventIds: IntArray): List<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg events: Event)

    @Delete
    fun delete(event: Event)
}