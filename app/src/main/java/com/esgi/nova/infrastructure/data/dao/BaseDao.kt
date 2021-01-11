package com.esgi.nova.infrastructure.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
abstract class BaseDao<Id, Entity> {


    abstract fun getAll(): List< @JvmSuppressWildcards Entity>

    abstract fun deleteAll()

    abstract fun loadAllByIds(ids: List<@JvmSuppressWildcards  Id>): List< @JvmSuppressWildcards Entity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(vararg entities: Entity)

    @Delete
    abstract fun delete(entity: Entity)
}