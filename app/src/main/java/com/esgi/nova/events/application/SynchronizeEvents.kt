package com.esgi.nova.events.application

import com.esgi.nova.events.infrastructure.api.EventApiRepository
import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceDbRepository
import com.esgi.nova.events.infrastructure.data.choices.ChoiceDbRepository
import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import com.esgi.nova.files.application.SynchronizeFile
import com.esgi.nova.infrastructure.fs.FsConstants
import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import javax.inject.Inject

class SynchronizeEvents @Inject constructor(
    private val eventDbRepository: EventDbRepository,
    private val choiceDbRepository: ChoiceDbRepository,
    private val eventApiRepository: EventApiRepository,
    private val choiceResourceDbRepository: ChoiceResourceDbRepository,
    private val synchronizeFile: SynchronizeFile,
    private val languageDbRepository: LanguageDbRepository
) {

    fun execute(language: String = languageDbRepository.getSelectedLanguage()?.tag ?: "" ) {
        val translatedEventsWrappers = eventApiRepository.getAllTranslatedEvents(language)


        eventDbRepository.insertAll(translatedEventsWrappers.map { it.data })

        translatedEventsWrappers.forEach { translatedEvent ->
            choiceDbRepository.insertAll(translatedEvent.data.choices)
            translatedEvent.data.choices.forEach { translatedChoice ->
                choiceResourceDbRepository.insertAll(translatedChoice.resources)
            }
        }

        translatedEventsWrappers.forEach { eventWrapper ->
            synchronizeFile.execute(eventWrapper.link.href, "${FsConstants.Paths.Events}${eventWrapper.data.id}")
        }
    }
}


