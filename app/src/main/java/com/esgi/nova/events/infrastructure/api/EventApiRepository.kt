package com.esgi.nova.events.infrastructure.api

import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.events.infrastructure.dto.TranslatedEventsWithBackgroundDto
import com.esgi.nova.infrastructure.api.apiBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class EventApiRepository @Inject constructor() {
    private var eventRequest: EventRequest? = null

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()

        eventRequest = retrofit.create(EventRequest::class.java)
    }

    fun getAllTranslatedEvents(language: String): List<TranslatedEventsWithBackgroundDto> =
        eventRequest?.getAllTranslatedEvents(language = language)?.execute()?.body() ?: listOf()
}