package com.esgi.nova.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.esgi.nova.data.entities.Resource

@Dao
interface ResourceDAO {

    @Query("SELECT * FROM resource")
    fun getAll(): List<Resource>

    @Query("DELETE FROM resource")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg resource: Resource)
}