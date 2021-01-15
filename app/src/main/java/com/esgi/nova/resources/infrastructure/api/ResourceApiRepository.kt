package com.esgi.nova.resources.infrastructure.api

import com.esgi.nova.infrastructure.api.ApiRepository
import com.esgi.nova.infrastructure.api.LinkWrapper
import com.esgi.nova.resources.ports.IResource
import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import retrofit2.Retrofit
import javax.inject.Inject

class ResourceApiRepository @Inject constructor(getUserToken: GetUserToken, updateUserToken: UpdateUserToken): ApiRepository(
    getUserToken,updateUserToken

) {

    private var resourcesServices: ResourceService

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()
        resourcesServices = retrofit.create(ResourceService::class.java)
    }

    fun getAll(language: String): List<LinkWrapper<IResource>> {
        return resourcesServices
            .getAll(language)
            .execute()
            .body()
            ?.map{LinkWrapper(it, it.iconUrl)}?: listOf()
    }
}