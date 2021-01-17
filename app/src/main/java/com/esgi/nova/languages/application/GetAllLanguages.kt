package com.esgi.nova.languages.application

import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import com.esgi.nova.languages.ports.IAppLanguage
import javax.inject.Inject

class GetAllLanguages @Inject constructor(private val languageDbRepository: LanguageDbRepository){

    fun execute(): List<IAppLanguage> = languageDbRepository.getAll()
}