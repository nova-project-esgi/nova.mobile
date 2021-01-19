package com.esgi.nova.files.infrastructure.ports

import java.io.InputStream

interface IFileStreamResume{
    val extension: String
    val data: InputStream
    val fileSize: Long
}

