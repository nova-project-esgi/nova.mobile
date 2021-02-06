package com.esgi.nova.parameters.application

import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import com.esgi.nova.parameters.infrastructure.storage.ParametersStorageRepository
import com.esgi.nova.parameters.ports.ILanguageParameters
import javax.inject.Inject

class GetParameters @Inject constructor(
    private val languageDbRepository: LanguageDbRepository,
    private val parametersStorageRepository: ParametersStorageRepository
) {

    suspend fun execute(): ILanguageParameters {
        return parametersStorageRepository
            .get()
            .toLanguageParameters(languageDbRepository.getSelectedLanguage())
    }

}

