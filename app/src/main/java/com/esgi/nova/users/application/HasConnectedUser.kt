package com.esgi.nova.users.application

import com.esgi.nova.users.infrastructure.api.AuthApiRepository
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import com.esgi.nova.users.ports.IHasConnectedUser
import javax.inject.Inject

class HasConnectedUser @Inject constructor(
    private val authApiRepository: AuthApiRepository,
    private val userStorageRepository: UserStorageRepository
) : IHasConnectedUser {
    override fun execute(): Boolean = userStorageRepository.getUserConnectionState() && userStorageRepository.getUser() != null
}