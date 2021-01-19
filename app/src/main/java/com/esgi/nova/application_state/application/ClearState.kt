package com.esgi.nova.application_state.application

import com.esgi.nova.application_state.storage.ApplicationStateStorageRepository
import com.esgi.nova.files.infrastructure.fs.FileStorageRepository
import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.parameters.infrastructure.storage.ParametersStorageRepository
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import javax.inject.Inject


class ClearState @Inject constructor(
    val db: AppDatabase,
    val fileStorageRepository: FileStorageRepository,
    val userStorageRepository: UserStorageRepository,
    val parametersStorageRepository: ParametersStorageRepository,
    val applicationStateStorageRepository: ApplicationStateStorageRepository
) {

    fun execute(){
        db.clear()
        fileStorageRepository.clear()
//        userStorageRepository.clear()
        parametersStorageRepository.clear()
        applicationStateStorageRepository.clear()
    }
}