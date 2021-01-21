package com.esgi.nova.application

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.esgi.nova.sound.services.BackgroundSoundService

class ApplicationSoundServiceObserver(private val context: Context): LifecycleObserver {

    companion object{
        val TAG: String = this::class.java.name
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
        BackgroundSoundService.start(context)
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        BackgroundSoundService.stop(context)
    }
}