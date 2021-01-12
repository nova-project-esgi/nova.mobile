package com.esgi.nova.resources.infrastructure.data

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class ResourceDAO : BaseDao<UUID, Resource>() {
    @Query("SELECT * FROM resources WHERE id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<Resource>

    @Delete
    abstract override fun delete(entity: Resource)

    @Query("SELECT * FROM resources")
    abstract override fun getAll(): List<Resource>

    @Query("DELETE FROM resources")
    abstract override fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: Resource)
}