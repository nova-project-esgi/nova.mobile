package com.esgi.nova.events.infrastructure.data.events

import com.esgi.nova.events.ports.IEvent
import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.util.*
import javax.inject.Inject

class EventDbRepository @Inject constructor(override val dao: EventDAO) :
    BaseRepository<UUID, EventEntity, IEvent>() {

    override fun toEntity(el: IEvent): EventEntity = el.reflectMapNotNull()
    override fun toEntities(entities: Collection<IEvent>): Collection<EventEntity> = entities.reflectMapCollection()


    fun getAllEventWithChoices() = dao.getAllEventWithChoices()

}


