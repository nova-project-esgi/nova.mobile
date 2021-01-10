package com.esgi.nova.events.application

import com.esgi.nova.events.infrastructure.api.EventRepository
import com.esgi.nova.events.infrastructure.dto.TranslatedEventsWithBackgroundDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SynchronizeEventsToLocalStorage {

    fun execute() {
        EventRepository.getAllTranslatedEvents(  object :
            Callback<List<TranslatedEventsWithBackgroundDto>> {
            override fun onResponse(
                call: Call<List<TranslatedEventsWithBackgroundDto>>,
                response: Response<List<TranslatedEventsWithBackgroundDto>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.forEach {

                        }
                    }
            }
        }

        override fun onFailure(call: Call<List<TranslatedEventsWithBackgroundDto>>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })
}}