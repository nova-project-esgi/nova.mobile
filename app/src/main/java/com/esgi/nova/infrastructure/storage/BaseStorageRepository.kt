package com.esgi.nova.infrastructure.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

abstract class BaseStorageRepository  constructor(private val context: Context) {

    protected abstract val preferenceKey: String

    protected val preference: SharedPreferences
        get() = context.getSharedPreferences(
            preferenceKey,
            Context.MODE_PRIVATE
        )
}