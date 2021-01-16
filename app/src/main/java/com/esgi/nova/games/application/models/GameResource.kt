package com.esgi.nova.games.application.models

import com.esgi.nova.games.ports.IGameResource
import java.util.*

data class GameResource(
    override val resourceId: UUID,
    override val gameId: UUID,
    override val total: Int
) : IGameResource {
}