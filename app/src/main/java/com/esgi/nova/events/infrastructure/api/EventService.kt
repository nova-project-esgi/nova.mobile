package com.esgi.nova.events.infrastructure.api

import com.esgi.nova.events.infrastructure.api.responses.TranslatedEventsWithBackgroundResponse
import com.esgi.nova.infrastructure.api.ApiConstants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*


interface EventService {

    @Headers("Accept: ${ApiConstants.CustomMediaType.Application.TranslatedEvent}")
    @GET("${ApiConstants.EndPoints.Events}${ApiConstants.EndPoints.Load}")
    suspend fun getAllTranslatedEvents(@Query("language") language: String): List<TranslatedEventsWithBackgroundResponse>

    @Headers("Accept: ${ApiConstants.CustomMediaType.Application.TranslatedEvent}")
    @GET("${ApiConstants.EndPoints.Events}${ApiConstants.EndPoints.Load}{eventId}")
    suspend fun getOneTranslatedEvent(
        @Path("eventId") eventId: String,
        @Query("language") language: String
    ): TranslatedEventsWithBackgroundResponse


    @Headers("Accept: ${ApiConstants.CustomMediaType.Application.TranslatedEvent}")
    @GET("${ApiConstants.EndPoints.Games}{gameId}/${ApiConstants.EndPoints.Daily}")
    suspend fun getDailyEvent(
        @Path("gameId") gameId: UUID,
        @Query("language") language: String
    ): TranslatedEventsWithBackgroundResponse
}