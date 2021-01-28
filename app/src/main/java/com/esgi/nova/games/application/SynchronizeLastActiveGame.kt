package com.esgi.nova.games.application

import android.util.Log
import com.esgi.nova.events.infrastructure.api.EventApiRepository
import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceDbRepository
import com.esgi.nova.events.infrastructure.data.choices.ChoiceDbRepository
import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import com.esgi.nova.files.application.SynchronizeFile
import com.esgi.nova.games.application.models.GameEvent
import com.esgi.nova.games.application.models.GameResource
import com.esgi.nova.games.exceptions.GameNotFoundException
import com.esgi.nova.games.infrastructure.api.GameApiRepository
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.infrastructure.data.game_event.GameEventDbRepository
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceDbRepository
import com.esgi.nova.games.ports.IGameState
import com.esgi.nova.infrastructure.fs.FsConstants
import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import com.esgi.nova.ports.Synchronize
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import com.esgi.nova.users.ports.IUserRecapped
import javax.inject.Inject

class SynchronizeLastActiveGame @Inject constructor(
    private val userStorageRepository: UserStorageRepository,
    private val gameApiRepository: GameApiRepository,
    private val gameDbRepository: GameDbRepository,
    private val gameResourceDbRepository: GameResourceDbRepository,
    private val gameEventDbRepository: GameEventDbRepository,
    private val eventDbRepository: EventDbRepository,
    private val eventApiRepository: EventApiRepository,
    private val synchronizeFile: SynchronizeFile,
    private val languageDbRepository: LanguageDbRepository,
    private val choiceDbRepository: ChoiceDbRepository,
    private val choiceResourceDbRepository: ChoiceResourceDbRepository
) : Synchronize {

    override suspend fun execute() {
        val language: String = languageDbRepository.getSelectedLanguage()?.tag ?: ""
        userStorageRepository.getUserResume()?.let { user ->
            try {
                gameApiRepository.getLastActiveGameForUser(username = user.username)
                    .let { gameState ->
                        val gameResources =
                            gameState.resources.map { resource -> resource.toGameResource(gameId = gameState.id) }
                        val gameEvents =
                            gameState.events.map { event -> event.toGameEvent(gameId = gameState.id) }
                        if (gameDbRepository.getActiveGameId(userId = user.id) != gameState.id) {
                            recreateGame(
                                user = user,
                                gameState = gameState,
                                gameResources = gameResources,
                                gameEvents = gameEvents,
                                language = language
                            )
                        } else {
                            updateExistingGame(
                                gameState = gameState,
                                gameResources = gameResources,
                                gameEvents = gameEvents
                            )
                        }
                    }
            } catch (e: GameNotFoundException) {
                Log.d(
                    SynchronizeLastActiveGame::class.qualifiedName,
                    "Last active game for user ${user.username} not found"
                )
            }

        }
    }

    private suspend fun updateExistingGame(
        gameState: IGameState,
        gameResources: List<GameResource>,
        gameEvents: List<GameEvent>
    ) {
        gameDbRepository.update(gameState)
        val gameResourcesEntities = gameResourceDbRepository.getAllById(gameState.id)
        val gameEventEntities = gameEventDbRepository.getAllById(gameState.id)
        gameResourceDbRepository.synchronizeCollection(
            gameResources,
            gameResourcesEntities
        ) { gameResource, gameResourceEntity -> gameResource.resourceId == gameResourceEntity.resourceId }
        gameEventDbRepository.synchronizeCollection(
            gameEvents,
            gameEventEntities
        ) { gamEvent, gameEventEntity -> gamEvent.eventId == gameEventEntity.eventId }
    }

    private suspend fun recreateGame(
        user: IUserRecapped,
        gameState: IGameState,
        gameResources: List<GameResource>,
        gameEvents: List<GameEvent>,
        language: String
    ) {
        gameDbRepository.setActiveGamesEnded(userId = user.id)
        gameDbRepository.insertOne(gameState)
        gameResourceDbRepository.insertAll(gameResources)

        gameEvents.forEach { gameEvent ->
            if (!eventDbRepository.exists(gameEvent.eventId)) {
                eventApiRepository.getOneTranslatedEvent(gameEvent.eventId, language)
                    .let { event ->
                        val choiceResources =
                            event.data.choices.flatMap { choice -> choice.resources }
                        eventDbRepository.insertOne(event.data)
                        choiceDbRepository.insertAll(event.data.choices)
                        choiceResourceDbRepository.insertAll(choiceResources)
                        synchronizeFile.execute(
                            event.link.href,
                            FsConstants.Paths.Events,
                            event.data.id.toString()
                        )
                    }
            }
        }
        gameEventDbRepository.insertAll(gameEvents)
    }
}