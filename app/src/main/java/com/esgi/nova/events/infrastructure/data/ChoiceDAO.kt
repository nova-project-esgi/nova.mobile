package com.esgi.nova.events.infrastructure.data

import androidx.room.*

@Dao
interface ChoiceDAO {
    @Query("SELECT * FROM choice")
    fun getAll(): List<Choice>

    @Query("DELETE FROM choice")
    fun deleteAll()

    @Query("SELECT * FROM choice WHERE id IN (:choiceIds)")
    fun loadAllByIds(choiceIds: IntArray): List<Choice>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg choices: Choice)

    @Delete
    fun delete(choice: Choice)
}