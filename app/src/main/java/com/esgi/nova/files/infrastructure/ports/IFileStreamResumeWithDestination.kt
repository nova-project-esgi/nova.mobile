package com.esgi.nova.files.infrastructure.ports

interface IFileStreamResumeWithDestination:
    IFileStreamResume {
    val destinationDir: String
    val fileNameWithoutExtension: String
}