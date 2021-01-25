package com.esgi.nova.games.infrastructure.api.models

import com.esgi.nova.games.ports.IGameEventState
import com.esgi.nova.games.ports.IGameResourceState
import com.esgi.nova.games.ports.IGameState
import java.util.*

data class GameState(
    override val resources: List<GameResourceState>,
    override val events: List<GameEventState>,
    override val id: UUID,
    override val difficultyId: UUID,
    override val duration: Int,
    override val isEnded: Boolean,
    override val userId: UUID
) : IGameState