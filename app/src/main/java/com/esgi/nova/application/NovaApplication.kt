package com.esgi.nova.application

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class NovaApplication : Application() {



    override fun onCreate() {
        super.onCreate()
        Lingver.init(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationSoundServiceObserver(this))
    }

}