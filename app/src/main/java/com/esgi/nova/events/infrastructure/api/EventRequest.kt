package com.esgi.nova.events.infrastructure.api

import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.events.infrastructure.dto.TranslatedEventsWithBackgroundDto
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Headers


interface EventRequest {

    @Headers("Accept: ${ApiConstants.CustomMediaType.Application.TranslatedEvent}")
    @GET("${ApiConstants.EndPoints.Events}${ApiConstants.EndPoints.Load}")
    fun getAllTranslatedEvents(@Field("language") language: String): Call<List<TranslatedEventsWithBackgroundDto>>?
}