package com.esgi.nova.events.infrastructure.api

import com.esgi.nova.events.infrastructure.api.responses.TranslatedEventsWithBackgroundResponse
import com.esgi.nova.infrastructure.api.ApiConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface EventService {

    @Headers("Accept: ${ApiConstants.CustomMediaType.Application.TranslatedEvent}")
    @GET("${ApiConstants.EndPoints.Events}${ApiConstants.EndPoints.Load}")
    fun getAllTranslatedEvents(@Query("language") language: String): Call<List<TranslatedEventsWithBackgroundResponse>>
}