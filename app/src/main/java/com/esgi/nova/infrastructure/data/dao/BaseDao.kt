package com.esgi.nova.infrastructure.data.dao

import androidx.room.*


@Dao
abstract class BaseDao<Id,  Entity> {


    abstract fun getAll(): List< @JvmSuppressWildcards Entity>

    abstract fun getById(id: Id): List<Entity>

    abstract fun deleteAll()

    abstract fun loadAllByIds(ids: List<@JvmSuppressWildcards  Id>): List< @JvmSuppressWildcards Entity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(vararg entities: Entity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOne(entity: Entity): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: Collection<@JvmSuppressWildcards Entity>): Unit

    @Update
    abstract fun update(obj: Entity)

    @Delete
    abstract fun delete(entity: Entity)
}