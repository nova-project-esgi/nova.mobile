package com.esgi.nova.users.application

import com.esgi.nova.users.dtos.ConnectedUserDto
import com.esgi.nova.users.dtos.UserLoginDto
import com.esgi.nova.users.infrastructure.api.AuthApiRepository
import retrofit2.Callback
import javax.inject.Inject

class LogUserWithToken @Inject constructor(private val authApiRepository: AuthApiRepository) {
    fun execute(token: String, callback: Callback<ConnectedUserDto>){
        authApiRepository.logWithToken(token, callback)
    }
}