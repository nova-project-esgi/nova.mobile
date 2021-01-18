package com.esgi.nova.games.infrastructure.data.game.models

import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import javax.inject.Inject

class CanResumeGame @Inject constructor(
    private val gameDbRepository: GameDbRepository
) {

    fun execute(): Boolean = gameDbRepository.getActiveGameId() != null
}