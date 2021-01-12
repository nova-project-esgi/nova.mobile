package com.esgi.nova.difficulties.infrastructure.data

import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.infrastructure.data.dao.BaseDao
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import java.util.*
import javax.inject.Inject

class DifficultyResourceDbRepository @Inject constructor(private val db: AppDatabase) :
    BaseRepository<UUID, DifficultyResource>() {

    override val dao: BaseDao<UUID, DifficultyResource>
        get() = db.difficultyResourceDAO()

}