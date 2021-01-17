package com.esgi.nova.infrastructure.data.repository

import com.esgi.nova.infrastructure.data.IIdEntity
import com.esgi.nova.infrastructure.data.dao.BaseDao
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult

abstract class BaseRepository<Id, Entity, Element> where Entity:Element, Element: IIdEntity<Id> {

    protected abstract val dao: BaseDao<Id, Entity>
    protected abstract fun toEntity(el: Element): Entity
    protected abstract fun toEntities(entities: Collection<Element>): Collection<Entity>

    fun getAll(): List<@JvmSuppressWildcards Element> = dao.getAll()
    fun getById(id: Id): Element? = dao.getById(id).firstOrNull()
    fun getAllById(id: Id): List<Element> = dao.getById(id)
    fun exists(id: Id): Boolean = getById(id) != null

    fun deleteAll() = dao.deleteAll()
    fun loadAllByIds(ids: List<Id>) = dao.loadAllByIds(ids)
    fun insertAll(entities: Collection<Element>) = dao.insertAll(toEntities(entities))
    fun insertOne(element: Element): Id{
        val entity = toEntity(element)
        dao.insertOne(entity)
        return entity.id
    }
    fun update(entity: Element) = dao.update(toEntity(entity))
    fun delete(entity: Element) = dao.delete(toEntity(entity))
    fun replace(id: Id, element: Element): Id{
        dao.getById(id).forEach { entity ->
            delete(entity)
        }
        return insertOne(element)
    }

    fun getAllAsync() = doAsyncResult { dao.getAll() }
    fun deleteAllAsync() = doAsync { dao.deleteAll() }
    fun loadAllByIdsAsync(ids: List<Id>) = doAsyncResult { dao.loadAllByIds(ids) }
    fun insertAllAsync(entities: Collection<Element>) = doAsync { insertAll(entities) }
    fun deleteAsync(entity: Element) = doAsync { delete(entity) }
}