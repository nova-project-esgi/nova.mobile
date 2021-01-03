package com.esgi.nova.network.auth

import com.esgi.nova.dto.ConnectedUserDTO
import com.esgi.nova.models.User
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthRepository {
    private var authRequest: AuthRequest ?= null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://freenetaccess.freeboxos.fr:8001/auth/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        AuthRepository.authRequest = retrofit.create(AuthRequest::class.java)
    }

    fun logWithUsernameAndPassword( username: String, password: String, callback: Callback<ConnectedUserDTO>)
    {
        val call = AuthRepository.authRequest?.logWithUsernameAndPassword(User(username, password))
        call?.enqueue(callback)
    }

    fun logWithToken(callback: Callback<ConnectedUserDTO>, token: String)
    {
        val call = AuthRepository.authRequest?.logWithToken(token)
        call?.enqueue(callback)
    }
}