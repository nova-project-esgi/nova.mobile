package com.esgi.nova.games.application.models

import com.esgi.nova.games.ports.IGameForCreation
import java.util.*

data class GameForCreation(
    override val username: String,
    override val difficultyId: UUID
) : IGameForCreation