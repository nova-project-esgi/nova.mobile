package com.esgi.nova.users.application

import com.esgi.nova.users.application.models.ConnectedUserPassword
import com.esgi.nova.users.exceptions.UserNotFoundException
import com.esgi.nova.users.infrastructure.api.AuthApiRepository
import com.esgi.nova.users.infrastructure.api.models.ConnectedUser
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import com.esgi.nova.users.ports.ILogUser
import com.esgi.nova.utils.reflectMap
import javax.inject.Inject


class LogUser @Inject constructor(private val authApiRepository: AuthApiRepository, private val userStorageRepository: UserStorageRepository){
    fun execute(userLogin: ILogUser){
        val connectedUser = authApiRepository.logUser(userLogin) ?: throw UserNotFoundException()
        userStorageRepository.saveUser(connectedUser.toConnectedUserPassword(userLogin.password))
    }
}