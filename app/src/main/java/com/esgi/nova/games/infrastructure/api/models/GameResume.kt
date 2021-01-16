package com.esgi.nova.games.infrastructure.api.models

import com.esgi.nova.games.ports.IGame
import java.util.*

data class GameResume(
    override val id: UUID,
    val userId: UUID,
    override val duration: Int,
    override val difficultyId: UUID,
    val resourceIds: List<UUID>,
    override val isEnded: Boolean,
    val eventIds: List<UUID>
): IGame
