package com.esgi.nova.files.infrastructure.api

import android.content.Context
import com.esgi.nova.files.infrastructure.ports.IFileStreamResume
import com.esgi.nova.infrastructure.api.AuthenticatedApiRepository
import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FileApiRepository @Inject constructor(
    @ApplicationContext context: Context,
    getUserToken: GetUserToken, updateUserToken: UpdateUserToken
) : AuthenticatedApiRepository(
    getUserToken,
    updateUserToken, context
) {
    private var fileService: FileService = apiBuilder()
        .build()
        .create(FileService::class.java)

    suspend fun getFile(fileUrl: String): IFileStreamResume {
        val file = fileService
            .getFile(fileUrl)
        return FileStreamResume(
            extension = file.contentType()?.subtype ?: file.contentType()?.type ?: "",
            data = file.byteStream(),
            fileSize = file.contentLength()
        )
    }

}