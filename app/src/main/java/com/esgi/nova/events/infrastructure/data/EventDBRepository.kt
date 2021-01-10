package com.esgi.nova.events.infrastructure.data

import android.app.Application
import com.esgi.nova.NovaApplication

class EventDBRepository {

    val db = AppDatabase.getAppDataBase(NovaApplication.getContext())

    fun insertEvent()


}