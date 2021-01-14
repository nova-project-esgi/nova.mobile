package com.esgi.nova.resources.infrastructure.data

import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.infrastructure.data.dao.BaseDao
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.resources.ports.IResource
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.util.*
import javax.inject.Inject

class ResourceDbRepository @Inject constructor(override val dao: ResourceDAO) : BaseRepository<UUID, ResourceEntity, IResource>() {

    override fun toEntities(entities: Collection<IResource>): Collection<ResourceEntity>  = entities.reflectMapCollection()

    override fun toEntity(el: IResource): ResourceEntity {
        return el.reflectMapNotNull()
    }
}