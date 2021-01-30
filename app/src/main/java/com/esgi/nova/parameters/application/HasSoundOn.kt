package com.esgi.nova.parameters.application

import com.esgi.nova.parameters.infrastructure.storage.ParametersStorageRepository
import javax.inject.Inject

class HasSoundOn @Inject constructor(
    private val parameterStorageRepository: ParametersStorageRepository
) {

    fun execute() = parameterStorageRepository.get().hasSound

}