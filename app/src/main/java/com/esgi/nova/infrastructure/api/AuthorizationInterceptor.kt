package com.esgi.nova.infrastructure.api

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val context: Context) : Interceptor {

    val token
        get() = context.getSharedPreferences(PreferenceConstants.UserKey, MODE_PRIVATE)
            .getString(PreferenceConstants.TokenKey, "")

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()


        if (request.header(HeaderConstants.Authorization) == null) {
            if (!token.isNullOrBlank()) {
                val finalToken = "Bearer $token"
                request = request.newBuilder()
                    .addHeader("Authorization", finalToken)
                    .build()
            }

        }

        return chain.proceed(request)
    }

}