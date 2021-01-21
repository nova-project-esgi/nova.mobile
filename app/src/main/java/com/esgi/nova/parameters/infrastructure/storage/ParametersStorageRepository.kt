package com.esgi.nova.parameters.infrastructure.storage

import android.content.Context
import com.esgi.nova.application_state.storage.models.Parameters
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import com.esgi.nova.infrastructure.storage.BaseStorageRepository
import com.esgi.nova.parameters.ports.IParameters
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ParametersStorageRepository @Inject constructor(@ApplicationContext context: Context): BaseStorageRepository(context) {

    override val preferenceKey: String = PreferenceConstants.Parameters.Key


    fun save(parameters: IParameters){
        with(preference.edit()) {
            putBoolean(PreferenceConstants.Parameters.HasDailyEventsKey,parameters.hasDailyEvents)
            putBoolean(PreferenceConstants.Parameters.HasMusicKey,parameters.hasSound)
            putBoolean(PreferenceConstants.Parameters.HasNotificationsKey,parameters.hasNotifications)
            putBoolean(PreferenceConstants.Parameters.IsDarkModeKey, parameters.isDarkMode)
            apply()
        }
    }

    fun get(): IParameters{
        return Parameters(
            isDarkMode = preference.getBoolean(PreferenceConstants.Parameters.IsDarkModeKey, true),
            hasDailyEvents = preference.getBoolean(PreferenceConstants.Parameters.HasDailyEventsKey, false),
            hasSound = preference.getBoolean(PreferenceConstants.Parameters.HasMusicKey, true),
            hasNotifications = preference.getBoolean(PreferenceConstants.Parameters.HasNotificationsKey, false)
        )
    }

    fun switchTheme(isDarkMode: Boolean): IParameters {
        with(preference.edit()) {
            putBoolean(PreferenceConstants.Parameters.IsDarkModeKey, isDarkMode)
            apply()
        }
        return get()
    }
    fun switchMusic(isOn: Boolean): IParameters {
        with(preference.edit()) {
            putBoolean(PreferenceConstants.Parameters.HasMusicKey, isOn)
            apply()
        }
        return get()
    }
    fun switchNotification(isOn: Boolean): IParameters {
        with(preference.edit()) {
            putBoolean(PreferenceConstants.Parameters.HasNotificationsKey, isOn)
            apply()
        }
        return get()
    }
}