package com.esgi.nova.files.infrastructure.api

import com.esgi.nova.files.infrastructure.ports.IFileStreamResume
import java.io.InputStream
import java.nio.file.Path

data class FileStreamResume(
    override val extension: String,
    override val data: InputStream,
    override val fileSize: Long

) : IFileStreamResume{
    

}