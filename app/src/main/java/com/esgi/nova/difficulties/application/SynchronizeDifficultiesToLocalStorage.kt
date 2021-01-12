package com.esgi.nova.difficulties.application

import com.esgi.nova.difficulties.difficultyResources
import com.esgi.nova.difficulties.infrastructure.api.DifficultyApiRepository
import com.esgi.nova.difficulties.infrastructure.data.Difficulty
import com.esgi.nova.difficulties.infrastructure.data.DifficultyDbRepository
import com.esgi.nova.difficulties.infrastructure.data.DifficultyResourceDbRepository
import com.esgi.nova.difficulties.infrastructure.dto.TranslatedDifficultyDto
import com.esgi.nova.utils.reflectMap
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SynchronizeDifficultiesToLocalStorage @Inject constructor(
    private val difficultiesDbRepository: DifficultyDbRepository,
    private val difficultiesApiRepository: DifficultyApiRepository,
    private val difficultiesResourceDbRepository: DifficultyResourceDbRepository
) {

    fun execute() {

        val difficulties = difficultiesApiRepository
            .getAllTranslatedDifficulties("en")
        difficulties.forEach{difficulty ->
            difficultiesDbRepository.insertAll(difficulty.reflectMapNotNull())
            difficultiesResourceDbRepository.insertAll(*difficulty.difficultyResources.toTypedArray())
        }
    }
}