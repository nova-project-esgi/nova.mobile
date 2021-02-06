package com.esgi.nova.parameters.application

import com.esgi.nova.parameters.infrastructure.storage.ParametersStorageRepository
import javax.inject.Inject

class HasNotifications @Inject constructor(
    private val parametersStorageRepository: ParametersStorageRepository
) {
    fun execute(): Boolean {
        return parametersStorageRepository.get().hasNotifications
    }

}