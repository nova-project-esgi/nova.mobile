package com.esgi.nova.events.infrastructure.data.choice_resource

import com.esgi.nova.events.ports.IChoiceResource
import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.util.*
import javax.inject.Inject

class ChoiceResourceDbRepository @Inject constructor(override val dao: ChoiceResourceDAO) :
    BaseRepository<UUID, ChoiceResourceEntity, IChoiceResource>() {


    fun getAllChoiceWithResource()  = dao.getAllChoiceWithResource()

    override fun toEntities(entities: Collection<IChoiceResource>): Collection<ChoiceResourceEntity>  = entities.reflectMapCollection()

    override fun toEntity(el: IChoiceResource): ChoiceResourceEntity { return el.reflectMapNotNull() }

}