package com.esgi.nova.languages.infrastructure.system

import java.util.*
import javax.inject.Inject

class LanguageSystemRepository @Inject constructor(){

    fun getLanguage(): String {
        return Locale.getDefault().toLanguageTag()
    }
}