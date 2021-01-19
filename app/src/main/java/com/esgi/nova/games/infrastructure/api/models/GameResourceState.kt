package com.esgi.nova.games.infrastructure.api.models

import com.esgi.nova.games.ports.IGameResourceState
import java.util.*

data class GameResourceState(override val resourceId: UUID, override val total: Int) :
    IGameResourceState