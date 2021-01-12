package com.esgi.nova.events.infrastructure.data.choices

import com.esgi.nova.infrastructure.data.AppDatabase
import javax.inject.Inject

class ChoiceDbRepository @Inject constructor(private val db: AppDatabase) {

    fun insert(choice: Choice) {
        db.choiceDAO().insertAll(choice)
    }

}