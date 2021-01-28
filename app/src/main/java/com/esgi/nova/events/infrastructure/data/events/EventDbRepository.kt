package com.esgi.nova.events.infrastructure.data.events

import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceDAO
import com.esgi.nova.events.ports.IEvent
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.util.*
import javax.inject.Inject

class EventDbRepository @Inject constructor(
    override val dao: EventDAO,
    val choiceResourceDAO: ChoiceResourceDAO
) :
    BaseRepository<UUID, EventEntity, IEvent>() {

    override fun toEntity(el: IEvent): EventEntity = el.reflectMapNotNull()
    override fun toEntities(entities: Collection<IEvent>): Collection<EventEntity> =
        entities.reflectMapCollection()


    suspend fun getAllEventWithChoices() = dao.getAllEventWithChoices()

    suspend fun getAllDetailedEvent() = dao.getAllEventWithChoices().map { eventWithChoices ->
        eventWithChoices.event.toDetailedEvent(eventWithChoices.choices
            .flatMap { choice ->
                choiceResourceDAO.getAllChoiceWithResourceByChoiceId(choice.id)
            })
    }

    suspend fun getDetailedEventById(id: UUID) =
        dao.getAllEventWithChoicesByEventId(id).firstOrNull()?.let { eventWithChoices ->
            eventWithChoices.event.toDetailedEvent(eventWithChoices.choices
                .flatMap { choice ->
                    choiceResourceDAO.getAllChoiceWithResourceByChoiceId(choice.id)
                })
        }

    suspend fun getAllDailyEvents(): List<IEvent> = dao.getAllByIsDaily(true)
    suspend fun getAllNonDailyEvents(): List<IEvent> = dao.getAllByIsDaily(false)


    suspend fun getCount() = dao.getAll().size

    suspend fun getAllNonDaily() = dao.getAllByIsDaily(false)


}


