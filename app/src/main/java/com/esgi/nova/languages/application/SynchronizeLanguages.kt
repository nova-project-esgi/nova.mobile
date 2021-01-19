package com.esgi.nova.languages.application

import com.esgi.nova.parameters.application.models.AppLanguage
import com.esgi.nova.languages.infrastructure.api.LanguageApiRepository
import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import com.esgi.nova.languages.infrastructure.system.LanguageSystemRepository
import com.esgi.nova.languages.ports.IDefaultLanguage
import com.esgi.nova.utils.reflectMapCollection
import javax.inject.Inject

class SynchronizeLanguages @Inject constructor(
    private val languageDbRepository: LanguageDbRepository,
    private val languageApiRepository: LanguageApiRepository,
    private val languageSystemRepository: LanguageSystemRepository

) {

    fun execute(selectedLanguage: String = languageSystemRepository.getLanguage()) {
        val languages = languageApiRepository.getAll();

        languageDbRepository.upsertCollection(languages.reflectMapCollection<IDefaultLanguage, AppLanguage>())
//        languageDbRepository.insertAll()
        
        val selectLanguage = languages.firstOrNull { it.tag == selectedLanguage }
            ?: languages.firstOrNull { it.isDefault }

        selectLanguage?.let {
            languageDbRepository.deselectLanguages()
            languageDbRepository.selectLanguage(selectLanguage.id)
        }
    }
}
