package com.esgi.nova.resources.infrastructure.api

import com.esgi.nova.infrastructure.api.apiBuilder
import com.esgi.nova.resources.dto.TranslatedResourceDto
import retrofit2.Retrofit
import javax.inject.Inject

class ResourceApiRepository @Inject constructor() {

    private var resourcesRequests: ResourcesRequests

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()
        resourcesRequests = retrofit.create(ResourcesRequests::class.java)
    }

    fun getAll(): List<TranslatedResourceDto> {
        return resourcesRequests.getAll()?.execute()?.body() ?: listOf()
    }
}