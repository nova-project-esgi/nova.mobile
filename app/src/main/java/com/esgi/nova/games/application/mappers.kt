package com.esgi.nova.games.application

import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.games.application.models.GameResource
import java.util.*

fun IDetailedDifficulty.getGameResources(gameId: UUID) = this.resources.map { resource ->
    GameResource(resourceId = resource.id, total = resource.startValue, gameId = gameId)
}