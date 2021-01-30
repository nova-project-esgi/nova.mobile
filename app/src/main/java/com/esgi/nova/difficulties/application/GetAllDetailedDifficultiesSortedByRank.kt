package com.esgi.nova.difficulties.application

import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceDbRepository
import javax.inject.Inject

class GetAllDetailedDifficultiesSortedByRank @Inject constructor(
    private val difficultiesResourceDbRepository: DifficultyResourceDbRepository
) {

    suspend fun execute() =
        difficultiesResourceDbRepository.getAllDetailedDifficulties().sortedBy { it.rank }
}