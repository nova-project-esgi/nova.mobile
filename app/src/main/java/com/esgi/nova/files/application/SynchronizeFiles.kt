package com.esgi.nova.files.application

import com.esgi.nova.files.dtos.FileSynchronizationDto
import com.esgi.nova.files.infrastructure.api.FileApiRepository
import com.esgi.nova.files.infrastructure.fs.FileStorageRepository
import javax.inject.Inject

class SynchronizeFiles @Inject constructor(
    private val fileApiRepository: FileApiRepository,
    private val fileStorageRepository: FileStorageRepository
) {

    fun execute(
        fileSynchronizations: List<FileSynchronizationDto>
    ) {
        val files = fileSynchronizations.mapNotNull { fileSynchro ->
            fileApiRepository.getFile(fileSynchro.url)?.toFileStreamWithDestination(
                fileSynchro.destinationDir,
                fileSynchro.fileName
            )
        }
        fileStorageRepository.upsertFiles(files = files)
    }
}