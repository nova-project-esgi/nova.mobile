package com.esgi.nova.events.infrastructure.data

import android.app.Application
import com.esgi.nova.NovaApplication

object EventDBRepository {

    val db = AppDatabase.getAppDataBase(NovaApplication.getContext())

    fun insertEvent(event: Event) {
        db?.eventDAO()?.insertAll(event)
    }

    fun insertChoice(choice: Choice) {
        db?.choiceDAO()?.insertAll(choice)
    }

}