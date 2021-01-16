package com.esgi.nova.games.infrastructure.data.game_resource.models

import com.esgi.nova.games.ports.IGameResourceEdition
import java.util.*

data class GameResourceEdition(override val resourceId: UUID, override val total: Int) : IGameResourceEdition {
}