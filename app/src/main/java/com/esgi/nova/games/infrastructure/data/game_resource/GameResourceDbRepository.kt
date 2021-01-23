package com.esgi.nova.games.infrastructure.data.game_resource

import com.esgi.nova.games.ports.IGameResource
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.util.*
import javax.inject.Inject

class GameResourceDbRepository @Inject constructor(override val dao: GameResourceDao) :
    BaseRepository<UUID, GameResourceEntity, IGameResource>() {


    override fun toEntity(el: IGameResource): GameResourceEntity = el.reflectMapNotNull()


    override fun toEntities(entities: Collection<IGameResource>): Collection<GameResourceEntity> = entities.reflectMapCollection()

    fun getByResourceIdAndGameId(gameId: UUID, resourceId: UUID) = dao.getByGameAndResourceId(gameId, resourceId).firstOrNull ()
}

