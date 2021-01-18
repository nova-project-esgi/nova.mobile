package com.esgi.nova.parameters.application

import com.esgi.nova.parameters.infrastructure.storage.ParametersStorageRepository
import javax.inject.Inject

class SwitchMusic @Inject constructor(
    private val parameterStorageRepository: ParametersStorageRepository
) : ThemeUseCase() {

    fun execute(isOn: Boolean) = parameterStorageRepository.switchMusic(isOn)

}


