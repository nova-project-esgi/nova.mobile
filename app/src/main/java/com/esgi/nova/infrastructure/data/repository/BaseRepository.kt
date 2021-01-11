package com.esgi.nova.infrastructure.data.repository

import com.esgi.nova.infrastructure.data.dao.BaseDao

abstract class BaseRepository<DAO: BaseDao<*,*>> {

//    protected abstract val db: AppDatabase

    protected abstract val dao: DAO get
}