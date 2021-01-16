package com.esgi.nova.games.infrastructure.api.models

import com.esgi.nova.games.ports.IGameResourceEdition
import java.util.*

class GameResourceEdition(override val resourceId: UUID, override val total: Int) : IGameResourceEdition {
}