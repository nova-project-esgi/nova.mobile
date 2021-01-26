package com.esgi.nova.infrastructure.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update


interface BaseDao<Id, Entity> {


    suspend fun getAll(): List<@JvmSuppressWildcards Entity>

    suspend fun getById(id: Id): List<@JvmSuppressWildcards Entity>

    suspend fun deleteAll()

    suspend fun loadAllByIds(ids: List<@JvmSuppressWildcards Id>): List<@JvmSuppressWildcards Entity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg entities: Entity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(entity: Entity): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: Collection<@JvmSuppressWildcards Entity>): Unit

    @Update
    suspend fun update(obj: Entity)

    @Delete
    suspend fun delete(entity: Entity)
}