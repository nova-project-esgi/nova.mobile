package com.esgi.nova.files.application

import com.esgi.nova.files.infrastructure.fs.FileStorageRepository
import javax.inject.Inject

class GetFileBitmapById @Inject constructor(
    private val fileStorageRepository: FileStorageRepository
) {
    fun <Id> execute(path: String, id: Id ) {
        fileStorageRepository.getBitMapFromPathById(path = path, id = id)
    }
}
