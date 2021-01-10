package com.esgi.nova

import android.app.Application
import android.content.Context

class NovaApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object{
        private lateinit var context: Context;
        fun getContext() = context
    }
}