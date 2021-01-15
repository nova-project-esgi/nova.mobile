package com.esgi.nova.games.infrastructure.data.game

import com.esgi.nova.games.infrastructure.data.game.GameDao
import com.esgi.nova.games.infrastructure.data.game.GameEntity
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.util.*
import javax.inject.Inject

class GameDbRepository @Inject constructor(override val dao: GameDao) :
    BaseRepository<UUID, GameEntity, IGame>() {


    override fun toEntity(el: IGame): GameEntity = el.reflectMapNotNull()


    override fun toEntities(entities: Collection<IGame>): Collection<GameEntity> = entities.reflectMapCollection()
}