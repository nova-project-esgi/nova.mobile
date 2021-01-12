package com.esgi.nova.languages.application

import com.esgi.nova.languages.infrastructure.api.LanguageApiRepository
import com.esgi.nova.languages.infrastructure.data.Language
import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import com.esgi.nova.languages.infrastructure.dto.LanguageDto
import com.esgi.nova.utils.reflectMapCollection
import javax.inject.Inject

class SynchronizeLanguagesToLocalStorage @Inject constructor(
    private val languageDbRepository: LanguageDbRepository,
    private val languageApiRepository: LanguageApiRepository
) {

    fun execute() {
        val languages = languageApiRepository.getAll()
            .reflectMapCollection<LanguageDto, Language>()
            .toTypedArray()
        languageDbRepository.insertAll(*languages)
    }
}

