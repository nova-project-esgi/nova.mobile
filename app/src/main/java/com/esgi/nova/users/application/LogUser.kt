package com.esgi.nova.users.application

import com.esgi.nova.users.dtos.ConnectedUserDto
import com.esgi.nova.users.dtos.UserLoginDto
import com.esgi.nova.users.infrastructure.api.AuthApiRepository
import javax.inject.Inject
import retrofit2.Callback


class LogUser @Inject constructor(private val authApiRepository: AuthApiRepository){
    fun execute(userLoginDto: UserLoginDto, callback: Callback<ConnectedUserDto>){
        authApiRepository.logWithUsernameAndPassword(userLoginDto.username, userLoginDto.password, callback)
    }
}