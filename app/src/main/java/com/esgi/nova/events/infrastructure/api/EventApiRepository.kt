package com.esgi.nova.events.infrastructure.api

import android.content.Context
import com.esgi.nova.events.ports.IResumedEvent
import com.esgi.nova.infrastructure.api.AuthenticatedApiRepository
import com.esgi.nova.infrastructure.api.LinkWrapper
import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class EventApiRepository @Inject constructor(
    @ApplicationContext context: Context,
    getUserToken: GetUserToken, updateUserToken: UpdateUserToken
) : AuthenticatedApiRepository(
    getUserToken,
    updateUserToken, context
) {
    private var eventService: EventService = apiBuilder()
        .build()
        .create(EventService::class.java)


    suspend fun getAllTranslatedEvents(language: String): List<LinkWrapper<IResumedEvent>> =
        eventService
            .getAllTranslatedEvents(language = language)
            .map { LinkWrapper(it.toResumedEvent(), it.backgroundUrl.toSecuredLink()) }


    suspend fun getOneTranslatedEvent(eventId: UUID, language: String): LinkWrapper<IResumedEvent> =
        eventService
            .getOneTranslatedEvent(eventId = eventId.toString(), language = language)
            .let { event ->
                LinkWrapper(
                    event.toResumedEvent(),
                    event.backgroundUrl.toSecuredLink()
                )
            }


    suspend fun getDailyEvent(language: String, gameId: UUID): LinkWrapper<IResumedEvent> =
        eventService
            .getDailyEvent(language = language, gameId = gameId)
            .let {
                LinkWrapper(it.toResumedEvent(), it.backgroundUrl.toSecuredLink())
            }

}