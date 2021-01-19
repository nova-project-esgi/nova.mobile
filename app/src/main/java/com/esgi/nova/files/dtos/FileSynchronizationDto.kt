package com.esgi.nova.files.dtos

data class FileSynchronizationDto(
    val url: String,
    val destinationDir: String,
    val fileName: String,
    val resolveFileExtension: Boolean = true
) {
}