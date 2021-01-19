package com.esgi.nova.infrastructure.storage

import android.content.Context
import android.content.SharedPreferences
import com.esgi.nova.infrastructure.Clear

abstract class BaseStorageRepository constructor(private val context: Context) :
    Clear {

    protected abstract val preferenceKey: String

    protected val preference: SharedPreferences
        get() = context.getSharedPreferences(
            preferenceKey,
            Context.MODE_PRIVATE
        )

    override fun clear() =
        with(preference.edit()) {
            clear()
            apply()
            true
        }

}