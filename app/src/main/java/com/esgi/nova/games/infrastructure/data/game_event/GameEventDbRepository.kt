package com.esgi.nova.games.infrastructure.data.game_event

import com.esgi.nova.games.infrastructure.data.game_event.GameEventDao
import com.esgi.nova.games.infrastructure.data.game_event.GameEventEntity
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.games.ports.IGameEvent
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.util.*
import javax.inject.Inject

class GameEventDbRepository @Inject constructor(override val dao: GameEventDao) :
    BaseRepository<UUID, GameEventEntity, IGameEvent>() {


    override fun toEntity(el: IGameEvent): GameEventEntity = el.reflectMapNotNull()


    override fun toEntities(entities: Collection<IGameEvent>): Collection<GameEventEntity> = entities.reflectMapCollection()

    fun getAllEventsByGameOrderByLinkTimeDesc(gameId: UUID):List<IGameEvent>{
        return getAllById(gameId).sortedByDescending { it.linkTime }
    }

}

