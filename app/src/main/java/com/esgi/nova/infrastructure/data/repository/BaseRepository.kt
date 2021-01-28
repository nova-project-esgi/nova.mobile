package com.esgi.nova.infrastructure.data.repository

import com.esgi.nova.infrastructure.data.IIdEntity
import com.esgi.nova.infrastructure.data.dao.BaseDao

abstract class BaseRepository<Id, Entity, Element> where Entity : Element, Element : IIdEntity<Id> {

    protected abstract val dao: BaseDao<Id, Entity>
    protected abstract fun toEntity(el: Element): Entity
    protected abstract fun toEntities(entities: Collection<Element>): Collection<Entity>

    suspend fun getAll(): List<@JvmSuppressWildcards Element> = dao.getAll()
    suspend fun getById(id: Id): Element? = dao.getById(id).firstOrNull()
    suspend fun getAllById(id: Id): List<Element> = dao.getById(id)
    suspend fun exists(id: Id): Boolean = getById(id) != null

    suspend fun deleteAll() = dao.deleteAll()
    suspend fun loadAllByIds(ids: List<Id>) = dao.loadAllByIds(ids)
    suspend fun insertAll(entities: Collection<Element>) = dao.insertAll(toEntities(entities))
    suspend fun insertOne(element: Element): Id {
        val entity = toEntity(element)
        dao.insertOne(entity)
        return entity.id
    }

    suspend fun update(entity: Element) = dao.update(toEntity(entity))
    suspend fun delete(entity: Element) = dao.delete(toEntity(entity))
    suspend fun deleteById(id: Id) {
        getById(id)?.let { entity ->
            delete(entity)
        }
    }

    suspend fun replace(id: Id, element: Element): Id {
        dao.getById(id).forEach { entity ->
            delete(entity)
        }
        return insertOne(element)
    }

    suspend fun upsertCollection(
        elements: Collection<Element>,
        entities: Collection<Element>,
        test: (el: Element, entity: Element) -> Boolean
    ) {
        elements.forEach { element ->
            if (entities.any { entity -> test.invoke(element, entity) }) {
                update(element)
            } else {
                insertOne(element)
            }
        }
    }

    suspend fun upsertCollection(elements: Collection<Element>) {
        val entities = dao.getAll()
        upsertCollection(elements, entities) { entity, element -> entity.id == element.id }
    }

    suspend fun upsertCollection(
        elements: Collection<Element>,
        test: (el: Element, entity: Element) -> Boolean
    ) {
        val entities = dao.getAll()
        upsertCollection(elements, entities, test)
    }

    suspend fun synchronizeCollection(
        elements: Collection<Element>,
        entities: Collection<Element>,
        test: (el: Element, entity: Element) -> Boolean
    ) {
        elements.forEach { element ->
            if (entities.any { entity -> test.invoke(element, entity) }) {
                update(element)
            } else {
                insertOne(element)
            }
        }
        entities.forEach { entity ->
            if (!elements.any { element -> test.invoke(element, entity) }) {
                delete(entity)
            }
        }
    }

    suspend fun synchronizeCollection(elements: Collection<Element>) {
        val entities = dao.getAll()
        synchronizeCollection(elements, entities) { entity, element -> entity.id == element.id }
    }

    suspend fun synchronizeCollection(
        elements: Collection<Element>,
        test: (el: Element, entity: Element) -> Boolean
    ) {
        val entities = dao.getAll()
        synchronizeCollection(elements, entities, test)
    }
}