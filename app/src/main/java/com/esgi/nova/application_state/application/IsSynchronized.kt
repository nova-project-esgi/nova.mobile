package com.esgi.nova.application_state.application

import com.esgi.nova.application_state.storage.ApplicationStateStorageRepository
import javax.inject.Inject

class IsSynchronized @Inject constructor(private val applicationStateStorageRepository: ApplicationStateStorageRepository){

    fun execute() = applicationStateStorageRepository.isSynchronized()
}

