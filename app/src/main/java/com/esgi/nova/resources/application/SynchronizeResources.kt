package com.esgi.nova.resources.application

import com.esgi.nova.files.application.SynchronizeFiles
import com.esgi.nova.files.dtos.FileSynchronizationDto
import com.esgi.nova.infrastructure.fs.FsConstants
import com.esgi.nova.languages.infrastructure.data.LanguageDbRepository
import com.esgi.nova.ports.Synchronize
import com.esgi.nova.resources.infrastructure.api.ResourceApiRepository
import com.esgi.nova.resources.infrastructure.data.ResourceDbRepository
import javax.inject.Inject

class SynchronizeResources @Inject constructor(
    private val resourceDbRepository: ResourceDbRepository,
    private val resourceApiRepository: ResourceApiRepository,
    private val synchronizeFiles: SynchronizeFiles,
    private val languageDbRepository: LanguageDbRepository
) : Synchronize {
    override suspend fun execute() {
        val language = languageDbRepository.getSelectedLanguage()?.tag ?: ""
        val resources = resourceApiRepository.getAll(language)

        val fileSynchronizations = resources.map { resourceWrapper ->
            FileSynchronizationDto(
                url = resourceWrapper.link.href,
                destinationDir = FsConstants.Paths.Resources,
                fileName = resourceWrapper.data.id.toString()
            )
        }
        synchronizeFiles.execute(fileSynchronizations)
        resourceDbRepository.synchronizeCollection(resources.map { it.data })
    }
}