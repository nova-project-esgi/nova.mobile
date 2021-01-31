package com.esgi.nova.files.application

import com.esgi.nova.files.infrastructure.api.FileApiRepository
import com.esgi.nova.files.infrastructure.fs.FileStorageRepository
import javax.inject.Inject

class SynchronizeFile @Inject constructor(
    private val fileApiRepository: FileApiRepository,
    private val fileStorageRepository: FileStorageRepository
) {

    suspend fun execute(
        url: String,
        destinationDir: String,
        fileName: String
    ) {
        fileApiRepository.getFile(url).let { fileStreamResume ->
            fileStorageRepository.saveFile(
                fileStreamResume.toFileStreamWithDestination(
                    destinationDir,
                    fileName
                )
            )
        }
    }
}


