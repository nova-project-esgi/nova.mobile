package com.esgi.nova.events.infrastructure.data

import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import javax.inject.Inject

class EventDbRepository @Inject constructor(private val db: AppDatabase):
    BaseRepository<EventDAO>() {

    override val dao: EventDAO
        get() = db.eventDAO()

    fun insertAll(vararg events: Event) {
        dao.insertAll(*events)
    }



}


