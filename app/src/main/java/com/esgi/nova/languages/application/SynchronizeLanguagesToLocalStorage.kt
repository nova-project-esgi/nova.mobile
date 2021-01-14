package com.esgi.nova.languages.application

import com.esgi.nova.languages.infrastructure.api.LanguageApiRepository
import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import javax.inject.Inject

class SynchronizeLanguagesToLocalStorage @Inject constructor(
    private val languageDbRepository: LanguageDbRepository,
    private val languageApiRepository: LanguageApiRepository
) {

    fun execute() {
        val languages = languageApiRepository.getAll()
        languageDbRepository.insertAll(languages)
    }
}

