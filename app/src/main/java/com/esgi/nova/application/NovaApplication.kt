package com.esgi.nova.application

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class NovaApplication : Application() {



    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationSoundServiceObserver(this))
    }

}