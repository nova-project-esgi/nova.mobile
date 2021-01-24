package com.esgi.nova.events.application

import com.esgi.nova.events.infrastructure.api.EventApiRepository
import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceDbRepository
import com.esgi.nova.events.infrastructure.data.choices.ChoiceDbRepository
import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import com.esgi.nova.files.application.SynchronizeFiles
import com.esgi.nova.files.dtos.FileSynchronizationDto
import com.esgi.nova.infrastructure.fs.FsConstants
import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import com.esgi.nova.ports.Synchronize
import javax.inject.Inject

class SynchronizeEvents @Inject constructor(
    private val eventDbRepository: EventDbRepository,
    private val choiceDbRepository: ChoiceDbRepository,
    private val eventApiRepository: EventApiRepository,
    private val choiceResourceDbRepository: ChoiceResourceDbRepository,
    private val synchronizeFiles: SynchronizeFiles,
    private val languageDbRepository: LanguageDbRepository
) : Synchronize {

    override fun execute() {
        val language = languageDbRepository.getSelectedLanguage()?.apiLocale ?: ""
        val translatedEventsWrappers = eventApiRepository.getAllTranslatedEvents(language)
        val translatedEvents = translatedEventsWrappers.map { it.data }
        val eventChoices = translatedEvents.flatMap { event -> event.choices }
        val choiceResources = eventChoices.flatMap { choice -> choice.resources }
        eventDbRepository.upsertCollection(translatedEvents)
        choiceDbRepository.upsertCollection(eventChoices)
        choiceResourceDbRepository.upsertCollection(choiceResources)

        val fileSynchronizations = translatedEventsWrappers.map { eventWrapper ->
            FileSynchronizationDto(
                url = eventWrapper.link.href,
                destinationDir = FsConstants.Paths.Events,
                fileName = eventWrapper.data.id.toString()
            )
        }
        synchronizeFiles.execute(fileSynchronizations)

    }
}


