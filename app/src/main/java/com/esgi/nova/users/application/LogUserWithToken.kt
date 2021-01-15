package com.esgi.nova.users.application

import com.esgi.nova.users.infrastructure.api.AuthApiRepository
import com.esgi.nova.users.ports.IConnectedUser
import javax.inject.Inject

class LogUserWithToken @Inject constructor(private val authApiRepository: AuthApiRepository) {
    fun execute(token: String): IConnectedUser? {
        return authApiRepository.logWithToken(token)
    }
}