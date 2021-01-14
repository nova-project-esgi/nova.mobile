package com.esgi.nova.files.infrastructure.ports

import java.io.InputStream
import java.nio.file.Path

interface IFileStreamResume{
    val extension: String
    val data: InputStream
    val fileSize: Long
}