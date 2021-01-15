package com.esgi.nova.infrastructure.api

import com.esgi.nova.users.application.GetUserToken
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationRequestInterceptor(val getUserToken: GetUserToken) : Interceptor {


    val token
        get() = getUserToken.execute()

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().signedRequest())
    }

    private fun Request.signedRequest(): Request {
        val finalToken = "Bearer $token"
        return this.newBuilder()
            .addHeader(HeaderConstants.Authorization, finalToken)
            .build()
    }


}

