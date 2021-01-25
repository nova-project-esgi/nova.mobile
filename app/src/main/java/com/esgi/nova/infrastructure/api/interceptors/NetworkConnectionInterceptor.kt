package com.esgi.nova.infrastructure.api.interceptors

import android.content.Context
import com.esgi.nova.infrastructure.api.exceptions.NoConnectionException
import com.esgi.nova.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected) {
            throw NoConnectionException()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private val isConnected: Boolean
        get() = NetworkUtils.isNetworkAvailable(context)

}