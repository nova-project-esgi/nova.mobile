package com.esgi.nova.resources.infrastructure.api

import com.esgi.nova.infrastructure.api.LinkWrapper
import com.esgi.nova.infrastructure.api.apiBuilder
import com.esgi.nova.resources.ports.IResource
import retrofit2.Retrofit
import javax.inject.Inject

class ResourceApiRepository @Inject constructor() {

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