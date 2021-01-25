package com.esgi.nova.languages.infrastructure.system

import android.content.Context
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject


class LanguageSystemRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getLanguage(): String {
        return Locale.getDefault().toLanguageTag()
    }

    fun updateResources(language: String, country: String): Context {
        Lingver.getInstance().setLocale(context, language, country)
        return context;
    }


}