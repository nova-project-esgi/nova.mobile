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


    fun getAllEventWithChoices() = dao.getAllEventWithChoices()

    fun getAllDetailedEvent() = dao.getAllEventWithChoices().map { eventWithChoices ->
        eventWithChoices.event.toDetailedEvent(eventWithChoices.choices
            .flatMap { choice ->
                choiceResourceDAO.getAllChoiceWithResourceByChoiceId(choice.id)
            })
    }

    fun getAllDetailedEventByIds(ids: List<UUID>) = dao.getAllEventWithChoices().map { eventWithChoices ->
        eventWithChoices.event.toDetailedEvent(eventWithChoices.choices
            .flatMap { choice ->
                choiceResourceDAO.getAllChoiceWithResourceByChoiceId(choice.id)
            })
    }


}


