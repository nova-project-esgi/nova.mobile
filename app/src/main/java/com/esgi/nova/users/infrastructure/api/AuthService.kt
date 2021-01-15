package com.esgi.nova.users.infrastructure.api

import com.esgi.nova.dtos.user.ConnectedUserDto
import com.esgi.nova.users.infrastructure.api.models.ConnectedUser
import com.esgi.nova.users.infrastructure.api.models.LogUser
import com.esgi.nova.users.ports.ILogUser
import retrofit2.Call
import retrofit2.http.*

interface AuthService {

    @POST("login")
    fun logWithUsernameAndPassword(@Body user: LogUser): Call<ConnectedUser>

    @FormUrlEncoded
    @GET()
    fun logWithToken(@Field("token") token: String): Call<ConnectedUser>
}