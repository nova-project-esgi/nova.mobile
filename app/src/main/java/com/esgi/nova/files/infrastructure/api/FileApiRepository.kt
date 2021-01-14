package com.esgi.nova.files.infrastructure.api

import com.esgi.nova.files.infrastructure.ports.IFileStreamResume
import com.esgi.nova.infrastructure.api.apiBuilder
import retrofit2.Retrofit
import java.nio.file.Path
import javax.inject.Inject

class FileApiRepository @Inject constructor() {
    private lateinit var fileService: FileService

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()
        fileService = retrofit.create(FileService::class.java)
    }

    fun getFile(fileUrl: String): IFileStreamResume? {
        return fileService
            .getFile(fileUrl)
            .execute()
            .body()
            ?.let {body ->
                return FileStreamResume(
                    extension = body.contentType()?.subtype() ?: body.contentType()?.type() ?: "",
                    data = body.byteStream(),
                    fileSize = body.contentLength()
                    )
            }
    }

}