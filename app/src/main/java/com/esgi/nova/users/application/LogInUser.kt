package com.esgi.nova.users.application

import com.esgi.nova.users.exceptions.InvalidPasswordException
import com.esgi.nova.users.exceptions.InvalidUsernameException
import com.esgi.nova.users.exceptions.UserNotFoundException
import com.esgi.nova.users.infrastructure.api.AuthApiRepository
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import com.esgi.nova.users.ports.ILogUser
import javax.inject.Inject
import kotlin.jvm.Throws


class LogInUser @Inject constructor(
    private val authApiRepository: AuthApiRepository,
    private val userStorageRepository: UserStorageRepository
) {
    @Throws(UserNotFoundException::class)
    suspend fun execute(userLogin: ILogUser) {
        val connectedUser = authApiRepository.logUser(userLogin)
        userStorageRepository.saveUser(connectedUser.toConnectedUserPassword(userLogin.password))
    }
}

