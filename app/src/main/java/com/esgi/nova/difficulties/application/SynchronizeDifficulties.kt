package com.esgi.nova.difficulties.application

import com.esgi.nova.difficulties.infrastructure.api.DifficultyApiRepository
import com.esgi.nova.difficulties.infrastructure.data.difficulty.DifficultyDbRepository
import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceDbRepository
import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import javax.inject.Inject

class SynchronizeDifficulties @Inject constructor(
    private val difficultiesDbRepository: DifficultyDbRepository,
    private val difficultiesApiRepository: DifficultyApiRepository,
    private val difficultiesResourceDbRepository: DifficultyResourceDbRepository,
    private val languageDbRepository: LanguageDbRepository
) {

    fun execute(language: String = languageDbRepository.getSelectedLanguage()?.tag ?: "" ) {

         val difficulties = difficultiesApiRepository
            .getAllTranslatedDifficulties(language)
        val difficultyResources = difficulties.flatMap { resumedDifficulty -> resumedDifficulty.resources }
        difficultiesDbRepository.upsertCollection(difficulties)
        difficultiesResourceDbRepository.upsertCollection(difficultyResources)
    }
}