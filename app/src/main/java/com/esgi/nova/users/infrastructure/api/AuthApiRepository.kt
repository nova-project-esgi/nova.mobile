package com.esgi.nova.users.infrastructure.api

import android.util.Log
import com.esgi.nova.infrastructure.api.ApiConstants

import com.esgi.nova.users.ports.IConnectedUser
import com.esgi.nova.users.ports.ILogUser
import com.esgi.nova.utils.reflectMapNotNull
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

    fun logUser(
        loginUser: ILogUser
    ): IConnectedUser? {
       return authService.logWithUsernameAndPassword(loginUser.reflectMapNotNull()).execute().body()
    }

    fun logWithToken(token: String): IConnectedUser? {
        Log.d("TEST",token)
        return authService.logWithToken(token).execute().body()
    }
}