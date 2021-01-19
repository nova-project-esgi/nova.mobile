package com.esgi.nova.events.infrastructure.api

import com.esgi.nova.events.ports.IResumedEvent
import com.esgi.nova.infrastructure.api.ApiRepository
import com.esgi.nova.infrastructure.api.LinkWrapper
import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject

class EventApiRepository @Inject constructor(getUserToken: GetUserToken, updateUserToken: UpdateUserToken): ApiRepository(getUserToken,updateUserToken) {
    private var eventService: EventService

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()

        eventService = retrofit.create(EventService::class.java)
    }

    fun getAllTranslatedEvents(language: String): List<LinkWrapper<IResumedEvent>> =
        eventService
            .getAllTranslatedEvents(language = language)
            .execute()
            .body()
            ?.map { LinkWrapper(it.toResumedEvent(), it.backgroundUrl) } ?: listOf()


    fun getOneTranslatedEvent(eventId: UUID, language: String): LinkWrapper<IResumedEvent>? =
        eventService
            .getOneTranslatedEvent(eventId = eventId.toString(), language = language)
            .execute()
            .body()
            ?.let { event -> LinkWrapper(event.toResumedEvent(), event.backgroundUrl) }


    fun getDailyEvent(language: String, gameId: UUID): LinkWrapper<IResumedEvent>? =
        eventService
            .getDailyEvent(language = language, gameId = gameId)
            .execute()
            .body()?.let {
                LinkWrapper(it.toResumedEvent(isDaily = true), it.backgroundUrl)
            }

}