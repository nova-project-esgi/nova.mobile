package com.esgi.nova.data.dao

import androidx.room.*
import com.esgi.nova.data.entities.Event

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