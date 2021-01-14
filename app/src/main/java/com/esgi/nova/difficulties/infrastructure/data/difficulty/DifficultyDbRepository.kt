package com.esgi.nova.difficulties.infrastructure.data.difficulty

import com.esgi.nova.difficulties.ports.IDifficulty
import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.infrastructure.data.dao.BaseDao
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.util.*
import javax.inject.Inject

class DifficultyDbRepository @Inject constructor(override val dao: DifficultyDAO) :
    BaseRepository<UUID, DifficultyEntity, IDifficulty>() {


    override fun toEntities(entities: Collection<IDifficulty>): Collection<DifficultyEntity>  = entities.reflectMapCollection()

    override fun toEntity(el: IDifficulty): DifficultyEntity {
        return el.reflectMapNotNull()
    }


}

