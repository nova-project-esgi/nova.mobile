package com.esgi.nova.resources.infrastructure.api

import android.content.Context
import com.esgi.nova.files.infrastructure.api.FileService
import com.esgi.nova.infrastructure.api.AuthenticatedApiRepository
import com.esgi.nova.infrastructure.api.LinkWrapper
import com.esgi.nova.resources.ports.IResource
import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Inject

class ResourceApiRepository @Inject constructor(
    @ApplicationContext context: Context,
    getUserToken: GetUserToken, updateUserToken: UpdateUserToken
) : AuthenticatedApiRepository(
    getUserToken, updateUserToken, context
) {

    private var resourcesServices: ResourceService = apiBuilder()
        .build()
        .create(ResourceService::class.java)

    fun getAll(language: String): List<LinkWrapper<IResource>> {
        return resourcesServices
            .getAll(language)
            .execute()
            .body()
            ?.map{LinkWrapper(it, it.iconUrl)}?: listOf()
    }
}