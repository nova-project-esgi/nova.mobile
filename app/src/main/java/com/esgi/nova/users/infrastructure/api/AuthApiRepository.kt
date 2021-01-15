package com.esgi.nova.users.infrastructure.api

import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.users.dtos.ConnectedUserDto
import com.esgi.nova.models.User
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthApiRepository @Inject constructor(){
    private var authService: AuthService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("${ApiConstants.BaseUrl}${ApiConstants.EndPoints.Auth}")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authService = retrofit.create(AuthService::class.java)
    }

    fun logWithUsernameAndPassword(
        username: String,
        password: String,
        callback: Callback<ConnectedUserDto>
    ) {
        val call = authService.logWithUsernameAndPassword(User(username, password))
        call.enqueue(callback)
    }

    fun logWithToken(callback: Callback<ConnectedUserDto>, token: String) {
        val call = authService.logWithToken(token)
        call.enqueue(callback)
    }
}