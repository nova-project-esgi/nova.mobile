package com.esgi.nova.games.infrastructure.data.game

import com.esgi.nova.games.infrastructure.data.game.models.GameEdition
import com.esgi.nova.games.infrastructure.data.game_event.GameEventDao
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceDao
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.games.ports.IGameEdition
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

    fun getGameEditionByID(id: UUID): IGameEdition? {
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
}