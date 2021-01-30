package com.esgi.nova.files.application

import com.esgi.nova.files.infrastructure.api.FileStreamResumeWithDestination
import com.esgi.nova.files.infrastructure.ports.IFileStreamResume

fun IFileStreamResume.toFileStreamWithDestination(
    destination: String,
    fileNameWithoutExtension: String
) =
    FileStreamResumeWithDestination(
        extension = extension,
        data = data,
        fileSize = fileSize,
        destinationDir = destination,
        fileNameWithoutExtension = fileNameWithoutExtension
    )