package com.esgi.nova.games.infrastructure.data.game.models

import com.esgi.nova.games.ports.IResumedGame
import com.esgi.nova.games.ports.ITotalValueResource
import java.util.*

data class ResumedGame(
    override val resources: MutableList<ITotalValueResource>,
    override val duration: Int,
    override val rounds: Int, override val id: UUID
) : IResumedGame {
}