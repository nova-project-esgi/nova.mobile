package com.esgi.nova.games.application

import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.infrastructure.data.game_event.GameEventDbRepository
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceDbRepository
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import javax.inject.Inject

class SynchronizeLastActiveGame @Inject constructor(
    private val userStorageRepository: UserStorageRepository,
    private val gameApiRepository: GameApiRepository,
    private val gameDbRepository: GameDbRepository,
    private val gameResourceDbRepository: GameResourceDbRepository,
    private val gameEventDbRepository: GameEventDbRepository
) {

    fun execute() {
        userStorageRepository.getUserResume()?.let { user ->
            gameApiRepository.getLastActiveGameForUser(username = user.username)?.let { gameState ->
                val gameResources =
                    gameState.resourceStates.map { resource -> resource.toGameResource(gameId = gameState.id) }
                val gameEvents =
                    gameState.eventStates.map { event -> event.toGameEvent(gameId = gameState.id) }
                if (gameDbRepository.getActiveGameId(userId = user.id) != gameState.id) {
                    gameDbRepository.setActiveGamesEnded(userId = user.id)
                    gameDbRepository.insertOne(gameState)
                    gameResourceDbRepository.insertAll(gameResources)
                    gameEventDbRepository.insertAll(gameEvents)
                } else {
                    gameDbRepository.update(gameState)
                    val gameResourcesEntities = gameResourceDbRepository.getAllById(gameState.id)
                    val gameEventEntities = gameEventDbRepository.getAllById(gameState.id)
                    gameResourceDbRepository.upsertCollection(
                        gameResources,
                        gameResourcesEntities
                    ) { gameResource, gameResourceEntity -> gameResource.resourceId == gameResourceEntity.resourceId }
                    gameEventDbRepository.upsertCollection(
                        gameEvents,
                        gameEventEntities
                    ) { gamEvent, gameEventEntity -> gamEvent.eventId == gameEventEntity.eventId }

                }
            }
        }
    }
}