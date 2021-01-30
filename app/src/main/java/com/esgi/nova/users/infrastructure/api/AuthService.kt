package com.esgi.nova.users.infrastructure.api

import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.infrastructure.api.error_handling.ExceptionsMapper
import com.esgi.nova.models.User
import com.esgi.nova.users.infrastructure.api.models.ConnectedUser
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("${ApiConstants.EndPoints.Auth}${ApiConstants.EndPoints.Login}")
    @ExceptionsMapper(UserExceptionMapper::class)
    suspend fun logWithUsernameAndPassword(@Body user: User): ConnectedUser


    @GET(".")
    fun logWithToken(@Query("token") token: String): ConnectedUser?
}