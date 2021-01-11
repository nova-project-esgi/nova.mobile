package com.esgi.nova

import android.app.Application
import android.content.Context
import com.esgi.nova.infrastructure.api.AuthorizationInterceptor
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient




@HiltAndroidApp
class NovaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        private lateinit var context: Context;
        fun getContext() = context
    }


    private fun setAuthorizationInterceptor(){
        val httpBuilder = OkHttpClient.Builder()
        val authInterceptor = AuthorizationInterceptor(this)
        val httpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(authInterceptor)
            .build()
    }
}