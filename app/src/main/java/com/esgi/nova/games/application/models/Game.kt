package com.esgi.nova.games.application.models

import com.esgi.nova.games.ports.IGame
import java.util.*

class Game(
    override val id: UUID,
    override val difficultyId: UUID,
    override val duration: Int,
    override val isEnded: Boolean,
    override val userId: UUID
) : IGame