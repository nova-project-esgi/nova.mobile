package com.esgi.nova.events.infrastructure.api

import com.esgi.nova.api.CustomMediaType
import com.esgi.nova.events.infrastructure.dto.TranslatedEventsWithBackgroundDto
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Headers


interface EventRequest {

    @Headers("Accept: ${CustomMediaType.Application.TranslatedEvent}")
    @GET("")
    fun getAllTranslatedEvents(@Field("language") language: String): Call<List<TranslatedEventsWithBackgroundDto>>?
}