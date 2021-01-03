package com.esgi.nova.network.auth

import com.esgi.nova.dto.ConnectedUserDTO
import com.esgi.nova.models.User
import retrofit2.Call
import retrofit2.http.*

interface AuthRequest {

    @POST("login")
    fun logWithUsernameAndPassword(@Body user: User): Call<ConnectedUserDTO>?

    @FormUrlEncoded
    @GET()
    fun logWithToken(@Field("token") token: String): Call<ConnectedUserDTO>?
}