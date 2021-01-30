package com.esgi.nova.application_state.storage

import android.content.Context
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import com.esgi.nova.infrastructure.storage.BaseStorageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApplicationStateStorageRepository @Inject constructor(
    @ApplicationContext context: Context

) :
    BaseStorageRepository(context) {
    override val preferenceKey: String = PreferenceConstants.ApplicationState.Key
    fun isSynchronized() =
        preference.getBoolean(PreferenceConstants.ApplicationState.IsSynchronizedKey, false)

    fun setSynchronizationState(isSynchronized: Boolean) =
        with(preference.edit()) {
            putBoolean(PreferenceConstants.ApplicationState.IsSynchronizedKey, isSynchronized)
            apply()
        }

}