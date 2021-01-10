package com.esgi.nova.events.infrastructure.api

import com.esgi.nova.events.infrastructure.dto.TranslatedEventsWithBackgroundDto
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EventRepository {
    private var eventRequest: EventRequest?= null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://freenetaccess.freeboxos.fr:8001/events/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        eventRequest = retrofit.create(EventRequest::class.java)
    }

    fun getAllTranslatedEvents(callback: Callback<List<TranslatedEventsWithBackgroundDto>>)
    {
        val language = "en" // ???
        val call = eventRequest?.getAllTranslatedEvents(language = language)
        call?.enqueue(callback)
    }
}