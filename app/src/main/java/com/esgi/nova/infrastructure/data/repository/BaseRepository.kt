package com.esgi.nova.infrastructure.data.repository

import com.esgi.nova.infrastructure.data.dao.BaseDao
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult

abstract class BaseRepository<Id, Entity, Element> where Entity:Element{

    protected abstract val dao:  BaseDao<Id, Entity >
    protected abstract fun toEntity(el: Element):Entity
    protected abstract fun toEntities(entities: Collection<Element>): Collection<Entity>

    fun getAll(): List<@JvmSuppressWildcards Entity> = dao.getAll()
    fun deleteAll() = dao.deleteAll()
    fun loadAllByIds(ids: List<Id>) = dao.loadAllByIds(ids)
    fun insertAll(entities: Collection<Element>) = dao.insertAll(toEntities(entities))
    fun insertOne(entity: Element) = dao.insertOne(toEntity(entity))
    fun delete(entity: Element) = dao.delete(toEntity(entity))


    fun getAllAsync() = doAsyncResult { dao.getAll() }
    fun deleteAllAsync() = doAsync { dao.deleteAll() }
    fun loadAllByIdsAsync(ids: List<Id>) = doAsyncResult { dao.loadAllByIds(ids) }
    fun insertAllAsync(entities: Collection<Element>) = doAsync { insertAll(entities) }
    fun deleteAsync(entity: Element) = doAsync { delete(entity) }
}