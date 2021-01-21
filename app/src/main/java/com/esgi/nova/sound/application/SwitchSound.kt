package com.esgi.nova.sound.application

import android.content.Context
import com.esgi.nova.sound.services.BackgroundSoundService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SwitchSound @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun execute(isOn: Boolean){
        if(isOn){
            BackgroundSoundService.start(context)
        } else {
            BackgroundSoundService.stop(context)
        }
    }
}