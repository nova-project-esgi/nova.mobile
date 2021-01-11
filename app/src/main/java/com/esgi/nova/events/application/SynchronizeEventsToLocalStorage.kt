package com.esgi.nova.events.application

import com.esgi.nova.events.infrastructure.api.EventApiRepository
import com.esgi.nova.events.infrastructure.data.Choice
import com.esgi.nova.events.infrastructure.data.ChoiceDbRepository
import com.esgi.nova.events.infrastructure.data.EventDbRepository
import com.esgi.nova.events.infrastructure.dto.TranslatedEventsWithBackgroundDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SynchronizeEventsToLocalStorage @Inject constructor(
    private val eventDbRepository: EventDbRepository,
    private val choiceDbRepository: ChoiceDbRepository,
    private val eventApiRepository: EventApiRepository
) {

    fun execute() {

        eventApiRepository.getAllTranslatedEvents(object :
            Callback<List<TranslatedEventsWithBackgroundDto>> {
            override fun onResponse(
                call: Call<List<TranslatedEventsWithBackgroundDto>>,
                response: Response<List<TranslatedEventsWithBackgroundDto>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { listOfEvents ->
                        listOfEvents.forEach { eventDTO ->
                            val event = com.esgi.nova.events.infrastructure.data.Event(
                                eventDTO.id,
                                eventDTO.title,
                                eventDTO.description
                            )
                            eventDbRepository.insertAll(event)

                            //todo : smth with backgroundurl

                            eventDTO.choices.forEach { choiceDTO ->
                                val choice =
                                    Choice(choiceDTO.id, choiceDTO.title, choiceDTO.description)
                                choiceDbRepository.insert(choice)

                                //todo : smth with backgroundurl

                                choiceDTO.resources.forEach { resourceDTO ->
                                    resourceDTO
                                }


                            }

                        }
                    }
                }
            }

            override fun onFailure(
                call: Call<List<TranslatedEventsWithBackgroundDto>>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }
        })
    }
}