package com.esgi.nova.languages.application

import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import com.esgi.nova.languages.ports.IAppLanguage
import javax.inject.Inject

class GetSelectedLanguage @Inject constructor(private val languageDbRepository: LanguageDbRepository) {

    suspend fun execute(): IAppLanguage? = languageDbRepository.getSelectedLanguage()
}