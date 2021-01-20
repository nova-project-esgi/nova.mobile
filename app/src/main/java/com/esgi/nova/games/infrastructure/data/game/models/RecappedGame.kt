package com.esgi.nova.games.infrastructure.data.game.models

import com.esgi.nova.games.ports.IRecappedGame
import com.esgi.nova.games.ports.ITotalValueResource
import java.util.*

data class RecappedGame(
    override val resources: MutableList<ITotalValueResource>,
    override val duration: Int,
    override val rounds: Int, override val id: UUID,
    override val difficultyId: UUID,
    override val isEnded: Boolean,
    override val userId: UUID
) : IRecappedGame {
}