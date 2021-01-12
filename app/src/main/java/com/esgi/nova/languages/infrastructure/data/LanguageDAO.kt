package com.esgi.nova.languages.infrastructure.data

import androidx.room.*
import com.esgi.nova.infrastructure.data.dao.BaseDao
import java.util.*

@Dao
abstract class LanguageDAO : BaseDao<UUID, Language>() {
    @Query("SELECT * FROM languages")
    abstract override fun getAll(): List<Language>

    @Query("DELETE FROM languages")
    abstract override fun deleteAll()

    @Query("SELECT * FROM languages WHERE id IN (:ids)")
    abstract override fun loadAllByIds(ids: List<UUID>): List<Language>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(vararg entities: Language)

    @Delete
    abstract override fun delete(entity: Language)
}