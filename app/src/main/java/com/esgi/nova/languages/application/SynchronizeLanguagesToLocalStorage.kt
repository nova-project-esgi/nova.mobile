package com.esgi.nova.languages.application

import com.esgi.nova.languages.application.models.AppLanguage
import com.esgi.nova.languages.infrastructure.api.LanguageApiRepository
import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import com.esgi.nova.languages.infrastructure.system.LanguageSystemRepository
import com.esgi.nova.languages.ports.IDefaultLanguage
import com.esgi.nova.utils.reflectMapCollection
import javax.inject.Inject

class SynchronizeLanguagesToLocalStorage @Inject constructor(
    private val languageDbRepository: LanguageDbRepository,
    private val languageApiRepository: LanguageApiRepository,
    private val languageSystemRepository: LanguageSystemRepository

) {

    fun execute(selectedLanguage: String = languageSystemRepository.getLanguage()) {
        val languages = languageApiRepository.getAll();

        languageDbRepository.insertAll(languages.reflectMapCollection<IDefaultLanguage, AppLanguage>())

        selectedLanguage.let {
            val selectLanguage = languages.firstOrNull { it.tag == selectedLanguage }
                ?: languages.firstOrNull { it.isDefault }

            selectLanguage?.let {
                languageDbRepository.deselectLanguages()
                languageDbRepository.selectLanguage(selectLanguage.id)
            }
        }
    }
}
