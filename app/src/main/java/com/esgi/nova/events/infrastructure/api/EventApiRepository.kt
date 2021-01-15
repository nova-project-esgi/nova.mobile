package com.esgi.nova.events.infrastructure.api

import com.esgi.nova.events.ports.IResumedEvent
import com.esgi.nova.infrastructure.api.ApiRepository
import com.esgi.nova.infrastructure.api.LinkWrapper
import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import retrofit2.Retrofit
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
}