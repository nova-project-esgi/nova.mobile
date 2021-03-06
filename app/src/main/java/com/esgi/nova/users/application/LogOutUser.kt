package com.esgi.nova.users.application

import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import javax.inject.Inject

class LogOutUser @Inject constructor(
    private val userStorageRepository: UserStorageRepository
) {
    fun execute() = userStorageRepository.changeConnectionState(false)
}

