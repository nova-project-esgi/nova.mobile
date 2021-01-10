package com.esgi.nova.difficulties.infrastructure.data

import androidx.room.*
import com.esgi.nova.events.infrastructure.data.Choice

@Dao
interface DifficultyDAO {

    @Query("SELECT * FROM difficulty")
    fun getAll(): List<Difficulty>

    @Query("DELETE FROM difficulty")
    fun deleteAll()

    @Query("SELECT * FROM difficulty WHERE id IN (:difficultyIds)")
    fun loadAllByIds(difficultyIds: IntArray): List<Difficulty>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg difficulties: Difficulty)

    @Delete
    fun delete(difficulty: Difficulty)
}