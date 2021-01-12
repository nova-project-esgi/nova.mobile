package com.esgi.nova.events.infrastructure.data.events

import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import java.util.*
import javax.inject.Inject

class EventDbRepository @Inject constructor(private val db: AppDatabase) :
    BaseRepository<UUID, Event>() {

    override val dao: EventDAO
        get() = db.eventDAO()


}


