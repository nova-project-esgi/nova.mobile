package com.esgi.nova.games.application

import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.ports.IGame
import javax.inject.Inject

class UpdateGame @Inject constructor(
    private val gameDbRepository: GameDbRepository
) {

    fun execute(game: IGame) =  gameDbRepository.update(game)


}