package com.esgi.nova.languages.infrastructure.data

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class LanguageDAO : BaseDao<UUID, LanguageEntity>() {
    @Query("SELECT * FROM languages")
    abstract override fun getAll(): List<LanguageEntity>

    @Query("SELECT * FROM languages WHERE is_selected = 1")
    abstract fun getSelectedLanguage(): List<LanguageEntity>

    @Query("SELECT * FROM languages WHERE id = :id")
    abstract override fun getById(id: UUID): List<LanguageEntity>

    @Query("DELETE FROM languages")
    abstract override fun deleteAll()

    @Query("SELECT * FROM languages WHERE id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<LanguageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: LanguageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(entities: Collection<LanguageEntity>): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertOne(entity: LanguageEntity): Unit

    @Delete
    abstract override fun delete(entity: LanguageEntity)
}