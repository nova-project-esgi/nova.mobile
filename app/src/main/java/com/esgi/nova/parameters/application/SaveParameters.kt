package com.esgi.nova.parameters.application

import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import com.esgi.nova.parameters.infrastructure.storage.ParametersStorageRepository
import com.esgi.nova.parameters.ports.ILanguageParameters
import javax.inject.Inject

class SaveParameters @Inject constructor(
    private val selectLanguage: SelectLanguage,
    private val parametersStorageRepository: ParametersStorageRepository
) {

    fun execute(params: ILanguageParameters): ILanguageParameters {
        parametersStorageRepository.save(params)
        params.selectedLanguage?.let { language ->
            selectLanguage.execute(languageId = language.id)?.let { selectedLanguage ->
                return params.toLanguageParameters(selectedLanguage)
            }
        }
        return params
    }

}