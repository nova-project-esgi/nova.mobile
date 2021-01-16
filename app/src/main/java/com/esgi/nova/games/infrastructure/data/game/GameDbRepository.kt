package com.esgi.nova.games.infrastructure.data.game

import com.esgi.nova.games.infrastructure.data.game.models.GameEdition
import com.esgi.nova.games.infrastructure.data.game.models.ResumedGame
import com.esgi.nova.games.infrastructure.data.game_event.GameEventDao
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceDao
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.games.ports.IGameEdition
import com.esgi.nova.games.ports.IResumedGame
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.util.*
import javax.inject.Inject

class GameDbRepository @Inject constructor(
    override val dao: GameDao,
    private val gameEventDao: GameEventDao,
    private val gameResourceDao: GameResourceDao
) :
    BaseRepository<UUID, GameEntity, IGame>() {


    override fun toEntity(el: IGame): GameEntity = el.reflectMapNotNull()


    override fun toEntities(entities: Collection<IGame>): Collection<GameEntity> =
        entities.reflectMapCollection()

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

    fun getResumedGameById(id: UUID): IResumedGame? {
        getById(id)?.let {game ->
            return ResumedGame(
                id = game.id,
                duration = game.duration,
                resources = gameResourceDao.getAllGameWithResourceById(id).map { it.toTotalValueResource() }.toMutableList(),
                rounds = gameEventDao.getEventsCountByGame(game.id)
            )
        }
        return null
    }

    fun getActiveGameId(): UUID? {
        return dao.getByIsEnded(false).firstOrNull()?.id
    }

    fun getActiveGamesIds(): List<UUID> {
        return dao.getByIsEnded(false).map { it.id }
    }

    fun setActiveGamesEnded(): List<UUID>{
        return dao.getByIsEnded(false).map {game ->
            game.isEnded = true
            dao.update(game)
            game.id
        }
    }
}