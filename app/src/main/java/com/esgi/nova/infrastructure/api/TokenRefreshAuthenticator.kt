package com.esgi.nova.infrastructure.api

import com.esgi.nova.users.application.UpdateUserToken
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenRefreshAuthenticator(val updateUserToken: UpdateUserToken) :
    Authenticator {


    override fun authenticate(route: Route?, response: Response): Request? = when {
        response.retryCount > 2 -> null
        else -> response.createSignedRequest()
    }

    private fun Request.signWithToken(accessToken: String) =
        newBuilder()
            .header(HeaderConstants.Authorization, "Bearer $accessToken")
            .build()

    private fun Response.createSignedRequest(): Request? = try {
        updateUserToken.execute()?.let { user ->
            this.request().signWithToken(user.token)
        }
    } catch (error: Throwable) {
        null
    }

    private val Response.retryCount: Int
        get() {
            var currentResponse = priorResponse()
            var result = 0
            while (currentResponse != null) {
                result++
                currentResponse = currentResponse.priorResponse()
            }
            return result
        }



}