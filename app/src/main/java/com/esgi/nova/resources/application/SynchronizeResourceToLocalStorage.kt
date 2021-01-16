package com.esgi.nova.resources.application

import com.esgi.nova.files.application.SynchronizeFile
import com.esgi.nova.files.infrastructure.api.FileApiRepository
import com.esgi.nova.infrastructure.fs.FsConstants
import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import com.esgi.nova.resources.infrastructure.api.ResourceApiRepository
import com.esgi.nova.resources.infrastructure.data.ResourceDbRepository
import javax.inject.Inject

class SynchronizeResourceToLocalStorage @Inject constructor(
    private val resourceDbRepository: ResourceDbRepository,
    private val resourceApiRepository: ResourceApiRepository,
    private val synchronizeFile: SynchronizeFile,
    private val languageDbRepository: LanguageDbRepository
) {

    fun execute(language: String = languageDbRepository.getSelectedLanguage()?.tag ?: "" ) {
        val resources = resourceApiRepository.getAll(language)
        resources.forEach { resourceWrapper ->
            synchronizeFile.execute(resourceWrapper.link.href, "${FsConstants.Paths.Resources}${resourceWrapper.data.id}")
        }
        resourceDbRepository.insertAll(resources.map { it.data })
    }
}