package com.esgi.nova.infrastructure.data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.esgi.nova.infrastructure.data.dao.BaseDao
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import java.util.concurrent.Future

abstract class BaseRepository<Id, Entity> {


    protected abstract val dao: BaseDao<Id, Entity> get

    fun getAll(): List<@JvmSuppressWildcards Entity> = dao.getAll()
    fun deleteAll() = dao.deleteAll()
    fun loadAllByIds(ids: List<Id>) = dao.loadAllByIds(ids)
    fun insertAll(vararg entities: Entity) = dao.insertAll(*entities)
    fun delete(entity: Entity) = dao.delete(entity)

    fun getAllAsync() = doAsyncResult { dao.getAll() }
    fun deleteAllAsync() = doAsync { dao.deleteAll() }
    fun loadAllByIdsAsync(ids: List<Id>) = doAsyncResult { dao.loadAllByIds(ids) }
    fun insertAllAsync(vararg entities: Entity) = doAsync { dao.insertAll(*entities) }
    fun deleteAsync(entity: Entity) = doAsync { dao.delete(entity) }
}