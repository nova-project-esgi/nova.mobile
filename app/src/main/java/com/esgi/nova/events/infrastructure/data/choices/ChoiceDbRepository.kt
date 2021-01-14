package com.esgi.nova.events.infrastructure.data.choices

import com.esgi.nova.events.ports.IChoice
import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.infrastructure.data.dao.BaseDao
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.util.*
import javax.inject.Inject

class ChoiceDbRepository @Inject constructor(override val dao: ChoiceDAO):
    BaseRepository<UUID, ChoiceEntity, IChoice>() {


    override fun toEntity(el:IChoice): ChoiceEntity = el.reflectMapNotNull()
    override fun toEntities(entities: Collection<IChoice>): Collection<ChoiceEntity>  = entities.reflectMapCollection()



}