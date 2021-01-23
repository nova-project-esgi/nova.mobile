package com.esgi.nova.games.infrastructure.data.game

import com.esgi.nova.games.infrastructure.data.game.models.GameEdition
import com.esgi.nova.games.infrastructure.data.game.models.RecappedGame
import com.esgi.nova.games.infrastructure.data.game_event.GameEventDao
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceDao
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.games.ports.IGameEdition
import com.esgi.nova.games.ports.IRecappedGame
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class GameDbRepository @Inject constructor(
    override val dao: GameDao,
    private val gameEventDao: GameEventDao,
    private val gameResourceDao: GameResourceDao
) :
    BaseRepository<UUID, GameEntity, IGame>() {


    override fun toEntity(el: IGame): GameEntity =  GameEntity(
        id = el.id,
        difficultyId = el.difficultyId,
        userId =  el.userId,
        duration = el.duration,
        isEnded = el.isEnded
    )


    override fun toEntities(entities: Collection<IGame>): Collection<GameEntity> = entities.map { toEntity(it) }

    fun getGameEditionById(id: UUID): IGameEdition? {
        val game = getById(id)
        game?.let {
            return GameEdition(
                duration = game.duration,
                events = gameEventDao.getAllGameWithEventById(id).map { it.toGameEventEdition() }.toMutableList(),
                resources = gameResourceDao.getAllGameWithResourceById(id).map { it.toGameResourceEdition() }.toMutableList(),
                isEnded = game.isEnded
            )
        }
        return null
    }

    fun getRecappedGameById(id: UUID): IRecappedGame? {
        getById(id)?.let {game ->
            return RecappedGame(
                id = game.id,
                duration = game.duration,
                resources = gameResourceDao.getAllGameWithResourceById(id)
                    .map { it.toTotalValueResource() }.toMutableList(),
                rounds = gameEventDao.getEventsCountByGame(game.id),
                difficultyId = game.difficultyId,
                userId = game.userId,
                isEnded = game.isEnded
            )
        }
        return null
    }

    fun hasDailyEventByDate(gameId: UUID, date: LocalDate): Boolean {
        return gameEventDao.getAllGameWithEventById(gameId)
            .any { gameWithEvent -> gameWithEvent.gameEvent.linkTime.toLocalDate() == date }
    }

    fun getActiveGameId(userId: UUID): UUID? {
        return dao.getByIsEndedAndUserId(false, userId).firstOrNull()?.id
    }

    fun getLastEndedGameId(userId: UUID): UUID? {
        return dao.getLast(true, userId).firstOrNull()?.id
    }
    fun getActiveGamesIds(userId: UUID): List<UUID> {
        return dao.getByIsEndedAndUserId(false, userId).map { it.id }
    }

    fun setActiveGamesEnded(userId: UUID): List<UUID> {
        return dao.getByIsEndedAndUserId(false, userId).map { game ->
            game.isEnded = true
            dao.update(game)
            game.id
        }
    }


}