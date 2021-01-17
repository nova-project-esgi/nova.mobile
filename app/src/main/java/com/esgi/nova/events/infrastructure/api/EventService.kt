package com.esgi.nova.events.infrastructure.api

import com.esgi.nova.events.infrastructure.api.responses.TranslatedEventsWithBackgroundResponse
import com.esgi.nova.infrastructure.api.ApiConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*


interface EventService {

    @Headers("Accept: ${ApiConstants.CustomMediaType.Application.TranslatedEvent}")
    @GET("${ApiConstants.EndPoints.Events}${ApiConstants.EndPoints.Load}")
    fun getAllTranslatedEvents(@Query("language") language: String): Call<List<TranslatedEventsWithBackgroundResponse>>

    @Headers("Accept: ${ApiConstants.CustomMediaType.Application.TranslatedEvent}")
    @GET("${ApiConstants.EndPoints.Games}{gameId}/${ApiConstants.EndPoints.Daily}")
    fun getDailyEvent(@Path("gameId")gameId: UUID,@Query("language") language: String): Call<TranslatedEventsWithBackgroundResponse?>
}