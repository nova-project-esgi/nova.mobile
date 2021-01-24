package com.esgi.nova.languages.infrastructure.system

import android.content.Context
import android.content.res.Resources
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject


class LanguageSystemRepository @Inject constructor(@ActivityContext private val context: Context){

    fun getLanguage(): String {
        return Locale.getDefault().toLanguageTag()
    }

    fun updateResources(language: String): Context {
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//        val resources: Resources = context.getResources()
//        val config = resources.configuration
//        config.setLocale(locale)
//        resources.updateConfiguration(config, resources.displayMetrics)


        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        return context.createConfigurationContext(config)
    }


}