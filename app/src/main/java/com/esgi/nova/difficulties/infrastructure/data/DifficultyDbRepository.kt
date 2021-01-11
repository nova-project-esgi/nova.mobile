package com.esgi.nova.difficulties.infrastructure.data

import com.esgi.nova.infrastructure.data.AppDatabase
import javax.inject.Inject

class DifficultyDbRepository @Inject constructor(private val db: AppDatabase) {

    fun insertAll(vararg  difficulties: Difficulty) {
        db.difficultyDAO().insertAll(*difficulties)
    }

}
