package com.esgi.nova.difficulties.application

import com.esgi.nova.difficulties.infrastructure.api.DifficultyApiRepository
import com.esgi.nova.difficulties.infrastructure.data.difficulty.DifficultyDbRepository
import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceDbRepository
import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import com.esgi.nova.ports.Synchronize
import javax.inject.Inject

class SynchronizeDifficulties @Inject constructor(
    private val difficultiesDbRepository: DifficultyDbRepository,
    private val difficultiesApiRepository: DifficultyApiRepository,
    private val difficultiesResourceDbRepository: DifficultyResourceDbRepository,
    private val languageDbRepository: LanguageDbRepository
) : Synchronize {

    override fun execute() {
        val language = languageDbRepository.getSelectedLanguage()?.tag ?: ""

        val difficulties = difficultiesApiRepository
            .getAllTranslatedDifficulties(language)
        val difficultyResources =
            difficulties.flatMap { resumedDifficulty -> resumedDifficulty.resources }
        difficultiesDbRepository.synchronizeCollection(difficulties)
        difficultiesResourceDbRepository.synchronizeCollection(difficultyResources)
    }
}