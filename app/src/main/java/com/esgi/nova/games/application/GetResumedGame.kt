package com.esgi.nova.games.application

import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.ports.IResumedGame
import java.util.*
import javax.inject.Inject

class GetResumedGame @Inject constructor(
    private val gameDbRepository: GameDbRepository
){

    fun execute(id: UUID): IResumedGame? = gameDbRepository.getResumedGameById(id)
}


