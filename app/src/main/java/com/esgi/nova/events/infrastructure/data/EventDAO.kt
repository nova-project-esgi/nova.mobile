package com.esgi.nova.events.infrastructure.data

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class EventDAO : BaseDao<UUID, Event> (){
    @Query("SELECT * FROM events")
    abstract override fun getAll(): List<Event>

    @Query("DELETE FROM events")
    abstract override fun deleteAll()

    @Query("SELECT * FROM events WHERE id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: Event)

    @Delete
    abstract override fun delete(entity: Event)
}