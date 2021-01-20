package com.esgi.nova.users.application

import com.esgi.nova.users.infrastructure.api.AuthApiRepository
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import javax.inject.Inject

class HasConnectedUser @Inject constructor(
    private val authApiRepository: AuthApiRepository,
    private val userStorageRepository: UserStorageRepository
) {
    fun execute(): Boolean = userStorageRepository.getUserConnectionState() && userStorageRepository.getUser() != null
}