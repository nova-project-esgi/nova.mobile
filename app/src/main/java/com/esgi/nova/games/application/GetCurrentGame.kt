package com.esgi.nova.games.application

import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.ports.IResumedGame
import javax.inject.Inject

class GetCurrentGame @Inject constructor(
    private val gameDbRepository: GameDbRepository
){

    fun execute(): IResumedGame? = gameDbRepository.getActiveGameId()?.let { id ->
        return gameDbRepository.getResumedGameById(id)
    }
}