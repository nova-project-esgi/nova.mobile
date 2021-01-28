package com.esgi.nova.users.infrastructure.api

import android.content.Context
import com.esgi.nova.infrastructure.api.ApiRepository
import com.esgi.nova.users.ports.IConnectedUser
import com.esgi.nova.users.ports.ILogUser
import com.esgi.nova.utils.reflectMapNotNull
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthApiRepository @Inject constructor(@ApplicationContext  context: Context): ApiRepository(context) {
    private var authService: AuthService = apiBuilder()
        .build()
        .create(AuthService::class.java)

    suspend fun logUser(
        loginUser: ILogUser
    ): IConnectedUser {
       return authService.logWithUsernameAndPassword(loginUser.reflectMapNotNull())
    }

    fun logWithToken(token: String): IConnectedUser? {
        return authService.logWithToken(token)
    }
}