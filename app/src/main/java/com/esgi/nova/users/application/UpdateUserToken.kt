package com.esgi.nova.users.application

import com.esgi.nova.users.infrastructure.api.AuthApiRepository
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import com.esgi.nova.users.ports.IConnectedUser
import javax.inject.Inject

class UpdateUserToken @Inject constructor(private val userStorageRepository: UserStorageRepository, private val userApiRepository: AuthApiRepository){

    suspend fun execute(): IConnectedUser?{
        userStorageRepository.getUser()?.let { user ->
            userApiRepository.logUser(user).let{ connectedUser ->
                userStorageRepository.saveUser(connectedUser.toConnectedUserPassword(user.password))
                return connectedUser
            }
        }
        return null
    }

}