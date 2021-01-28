package com.esgi.nova.games.infrastructure.data.game.models

import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import javax.inject.Inject

class CanResumeGame @Inject constructor(
    private val gameDbRepository: GameDbRepository,
    private val userStorageRepository: UserStorageRepository
) {

    suspend fun execute(): Boolean = userStorageRepository.getUserId()
        ?.let { userId -> gameDbRepository.getActiveGameId(userId) } != null
}