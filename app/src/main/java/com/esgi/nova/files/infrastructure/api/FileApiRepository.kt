package com.esgi.nova.files.infrastructure.api

import android.content.Context
import com.esgi.nova.events.infrastructure.api.EventService
import com.esgi.nova.files.infrastructure.ports.IFileStreamResume
import com.esgi.nova.infrastructure.api.AuthenticatedApiRepository
import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
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