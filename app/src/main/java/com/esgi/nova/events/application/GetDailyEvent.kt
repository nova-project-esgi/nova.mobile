package com.esgi.nova.events.application

import com.esgi.nova.events.infrastructure.api.EventApiRepository
import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceDbRepository
import com.esgi.nova.events.infrastructure.data.choices.ChoiceDbRepository
import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import com.esgi.nova.events.infrastructure.data.events.models.DetailedEvent
import com.esgi.nova.files.application.SynchronizeFile
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.infrastructure.fs.FsConstants
import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class GetDailyEvent @Inject constructor(
    private val eventDbRepository: EventDbRepository,
    private val choiceDbRepository: ChoiceDbRepository,
    private val eventApiRepository: EventApiRepository,
    private val choiceResourceDbRepository: ChoiceResourceDbRepository,
    private val synchronizeFile: SynchronizeFile,
    private val languageDbRepository: LanguageDbRepository,
    private val gameDbRepository: GameDbRepository
) {

    fun execute(
        gameId: UUID,
        language: String = languageDbRepository.getSelectedLanguage()?.androidLocale ?: ""
    ): DetailedEvent? {

        if (gameDbRepository.hasDailyEventByDate(gameId, LocalDate.now())) {
            return null
        }

        eventApiRepository.getDailyEvent(language, gameId)?.let { eventWrapper ->
            eventDbRepository.insertOne(eventWrapper.data)
            choiceDbRepository.insertAll(eventWrapper.data.choices)
            eventWrapper.data.choices.forEach { choice ->
                choiceResourceDbRepository.insertAll(choice.resources)
            }
            synchronizeFile.execute(
                eventWrapper.link.href,
                FsConstants.Paths.Events,
                eventWrapper.data.id.toString()
            )
            return eventDbRepository.getDetailedEventById(eventWrapper.data.id)
        }
        return null
    }
}

