package com.esgi.nova.games.infrastructure.api.models

import com.esgi.nova.games.ports.IGame
import java.util.*

data class GameResume(
    override val id: UUID,
    val userId: UUID,
    val duration: Int,
    override val difficultyId: UUID,
    val resourceIds: List<UUID>,
    val isEnded: Boolean,
    val eventIds: List<UUID>
): IGame
