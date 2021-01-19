package com.esgi.nova.games.application

import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.files.application.model.FileWrapper
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.application.models.GameEvent
import com.esgi.nova.games.application.models.GameResource
import com.esgi.nova.games.application.models.RecappedGameWithResourceIcons
import com.esgi.nova.games.ports.IRecappedGame
import com.esgi.nova.games.ports.*
import com.esgi.nova.resources.ports.IResource
import java.util.*

fun IDetailedDifficulty.getGameResources(gameId: UUID) = this.resources.map { resource ->
    GameResource(resourceId = resource.id, total = resource.startValue, gameId = gameId)
}

fun IRecappedGame.toRecappedGameWithResourceIcons(resourceWrappers: List<IFileWrapper<IResource>>) =
    RecappedGameWithResourceIcons(
        id = this.id,
        resources = this.resources.mapNotNull { resource ->
            resourceWrappers.firstOrNull { resourceWrapper ->
                resourceWrapper.data.id == resource.id
            }?.let { resourceWrapper ->
                FileWrapper(resource, resourceWrapper.img)
            }
        },
        duration = this.duration,
        rounds = this.rounds
    )

fun IGameResourceState.toGameResource(gameId: UUID) = GameResource(
    resourceId = resourceId,
    gameId = gameId,
    total = total
)

fun IGameEventState.toGameEvent(gameId: UUID) = GameEvent(
    eventId = eventId,
    gameId = gameId,
    linkTime = linkTime
)