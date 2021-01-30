package com.esgi.nova.sound.infrastructure.storage

import android.content.Context
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import com.esgi.nova.infrastructure.storage.BaseStorageRepository
import com.esgi.nova.sound.infrastructure.storage.models.SoundResume
import com.esgi.nova.sound.ports.ISoundResume
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SoundStorageRepository @Inject constructor(@ApplicationContext context: Context) :
    BaseStorageRepository(
        context
    ) {
    override val preferenceKey: String = PreferenceConstants.Sound.Key

    fun get(): ISoundResume = SoundResume(
        backgroundSoundPosition = preference.getInt(
            PreferenceConstants.Sound.BackgroundSoundPositionKey,
            0
        )
    )

    fun save(soundResume: ISoundResume) {
        with(preference.edit()) {
            putInt(
                PreferenceConstants.Sound.BackgroundSoundPositionKey,
                soundResume.backgroundSoundPosition
            )
            apply()
        }
    }

}