package com.esgi.nova.events.application

import com.esgi.nova.events.infrastructure.api.EventRepository
import com.esgi.nova.events.infrastructure.data.Choice
import com.esgi.nova.events.infrastructure.data.EventDBRepository
import com.esgi.nova.events.infrastructure.dto.TranslatedEventsWithBackgroundDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SynchronizeEventsToLocalStorage {

    fun execute() {
        EventRepository.getAllTranslatedEvents(  object :
            Callback<List<TranslatedEventsWithBackgroundDto>> {
            override fun onResponse(
                call: Call<List<TranslatedEventsWithBackgroundDto>>,
                response: Response<List<TranslatedEventsWithBackgroundDto>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { listOfEvents ->
                        listOfEvents.forEach { eventDTO ->
                            val event = com.esgi.nova.events.infrastructure.data.Event(eventDTO.id, eventDTO.title, eventDTO.description )
                            EventDBRepository.insertEvent(event)

                            //todo : smth with backgroundurl

                            eventDTO.choices.forEach { choiceDTO ->
                                val choice = Choice(choiceDTO.id, choiceDTO.title, choiceDTO.description)
                                EventDBRepository.insertChoice(choice)

                                //todo : smth with backgroundurl

                                choiceDTO.resources.forEach { resourceDTO ->
                                    resourceDTO
                                }


                            }

                        }
                    }
            }
        }

        override fun onFailure(call: Call<List<TranslatedEventsWithBackgroundDto>>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })
}}