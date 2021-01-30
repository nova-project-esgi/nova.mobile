package com.esgi.nova.application

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.esgi.nova.sound.services.BackgroundSoundService

class ApplicationSoundServiceObserver(private val context: Context) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
        try {
            BackgroundSoundService.start(context)
        } catch (e: Exception) {

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        try {
            BackgroundSoundService.stop(context)
        } catch (e: Exception) {

        }
    }
}