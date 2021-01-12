package com.esgi.nova.resources.infrastructure.data

import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.infrastructure.data.dao.BaseDao
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import java.util.*
import javax.inject.Inject

class ResourceDbRepository @Inject constructor(private val db: AppDatabase) : BaseRepository<UUID, Resource>() {
    override val dao: BaseDao<UUID, Resource>
        get() = db.resourceDAO()
}