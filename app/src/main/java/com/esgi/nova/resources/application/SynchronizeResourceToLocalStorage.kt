package com.esgi.nova.resources.application

import com.esgi.nova.resources.dto.TranslatedResourceDto
import com.esgi.nova.resources.infrastructure.api.ResourceApiRepository
import com.esgi.nova.resources.infrastructure.data.Resource
import com.esgi.nova.resources.infrastructure.data.ResourceDbRepository
import com.esgi.nova.utils.reflectMapCollection
import javax.inject.Inject

class SynchronizeResourceToLocalStorage @Inject constructor(
    private val resourceDbRepository: ResourceDbRepository,
    private val resourceApiRepository: ResourceApiRepository
) {

    fun execute() {
        val resources = resourceApiRepository.getAll()
            .reflectMapCollection<TranslatedResourceDto, Resource>()
            .toTypedArray()
        resourceDbRepository.insertAll(*resources)
    }
}