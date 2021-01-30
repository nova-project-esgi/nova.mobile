package com.esgi.nova.parameters.application

import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import com.esgi.nova.languages.ports.IAppLanguage
import com.esgi.nova.parameters.application.models.AppLanguage
import java.util.*
import javax.inject.Inject

class SelectLanguage @Inject constructor(private val languageDbRepository: LanguageDbRepository) {

    suspend fun execute(languageId: UUID): IAppLanguage? {
        languageDbRepository.getById(languageId)?.let { language ->
            val selectedLanguage =
                AppLanguage(
                    isSelected = true,
                    id = languageId,
                    code = language.code,
                    subCode = language.subCode
                )
            languageDbRepository.deselectLanguages()
            languageDbRepository.update(selectedLanguage)
            return selectedLanguage
        }
        return null
    }

}


