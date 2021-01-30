package com.esgi.nova.users.application

import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import javax.inject.Inject

class GetUserToken @Inject constructor(private val userStorageRepository: UserStorageRepository) {
    fun execute() = userStorageRepository.getUserToken()
}