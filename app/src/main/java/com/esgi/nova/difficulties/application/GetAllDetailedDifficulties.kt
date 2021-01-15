package com.esgi.nova.difficulties.application

import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceDbRepository
import javax.inject.Inject

class GetAllDetailedDifficulties @Inject constructor(
    private val difficultiesResourceDbRepository: DifficultyResourceDbRepository
) {

    fun execute()  = difficultiesResourceDbRepository.getAllDetailedDifficulties()
}