package com.esgi.nova.games.application

import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.ports.IRecappedGame
import java.util.*
import javax.inject.Inject

class GetRecappedGame @Inject constructor(
    private val gameDbRepository: GameDbRepository
){

    fun execute(id: UUID): IRecappedGame? = gameDbRepository.getRecappedGameById(id)
}


