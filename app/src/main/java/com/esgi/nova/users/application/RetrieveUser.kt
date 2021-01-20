package com.esgi.nova.users.application

import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import com.esgi.nova.users.ports.ILogUser
import javax.inject.Inject

class RetrieveUser @Inject constructor(
    private val userStorageRepository: UserStorageRepository
) {
    fun execute(): ILogUser? = userStorageRepository.getUser()
}