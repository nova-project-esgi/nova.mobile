package com.esgi.nova.difficulties.application

import com.esgi.nova.difficulties.infrastructure.api.DifficultyApiRepository
import com.esgi.nova.difficulties.infrastructure.data.difficulty.DifficultyDbRepository
import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceDbRepository
import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import javax.inject.Inject

class SynchronizeDifficultiesToLocalStorage @Inject constructor(
    private val difficultiesDbRepository: DifficultyDbRepository,
    private val difficultiesApiRepository: DifficultyApiRepository,
    private val difficultiesResourceDbRepository: DifficultyResourceDbRepository,
    private val languageDbRepository: LanguageDbRepository
) {

    fun execute(language: String = languageDbRepository.getSelectedLanguage()?.tag ?: "" ) {

         val difficulties = difficultiesApiRepository
            .getAllTranslatedDifficulties(language)
        difficulties.forEach { difficulty ->
            difficultiesDbRepository.insertOne(difficulty)
            difficultiesResourceDbRepository.insertAll(difficulty.resources)
        }
        difficultiesResourceDbRepository.getAll()
            .forEach { difficultyResource -> println(difficultyResource) }
        difficultiesResourceDbRepository.getAllDetailedDifficulties()
    }
}