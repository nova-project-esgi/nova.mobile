package com.esgi.nova.files.infrastructure.api

import com.esgi.nova.files.infrastructure.ports.IFileStreamResumeWithDestination
import java.io.InputStream

data class FileStreamResumeWithDestination(
    override val extension: String,
    override val data: InputStream,
    override val fileSize: Long,
    override val destinationDir: String, override val fileNameWithoutExtension: String
) : IFileStreamResumeWithDestination {


}