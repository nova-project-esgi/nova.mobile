package com.esgi.nova.application_state.storage

import android.content.Context
import android.content.SharedPreferences
import android.text.method.TextKeyListener.clear
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import com.esgi.nova.infrastructure.storage.BaseStorageRepository
import javax.inject.Inject

class ApplicationStateStorageRepository @Inject constructor(
    context: Context

) :
    BaseStorageRepository(context) {
    override val preferenceKey: String = PreferenceConstants.ApplicationState.Key
    fun isSynchronized() =
        preference.getBoolean(PreferenceConstants.ApplicationState.IsSynchronizedKey, false)

    fun setSynchronized() =
        with(preference.edit()) {
            putBoolean(PreferenceConstants.ApplicationState.IsSynchronizedKey, true)
            apply()
        }

}