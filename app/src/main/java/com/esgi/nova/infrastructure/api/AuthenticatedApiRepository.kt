package com.esgi.nova.infrastructure.api

import android.content.Context
import com.esgi.nova.infrastructure.api.error_handling.ErrorsCallAdapterFactory
import com.esgi.nova.infrastructure.api.interceptors.AuthorizationRequestInterceptor
import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


open class AuthenticatedApiRepository @Inject constructor(
    private val getUserToken: GetUserToken,
    private val updateUserToken: UpdateUserToken,
    @ApplicationContext context: Context
) : ApiRepository(context) {

    override fun apiBuilder(baseUrl: String): Retrofit.Builder {
        val authRequestInterceptor =
            AuthorizationRequestInterceptor(this@AuthenticatedApiRepository.getUserToken)
        val authResponseInterceptor =
            TokenRefreshAuthenticator(this@AuthenticatedApiRepository.updateUserToken)
        val client = httpClientBuilder
            .addNetworkInterceptor(authRequestInterceptor)
            .authenticator(authResponseInterceptor)
            .build()
        return Retrofit.Builder().baseUrl(baseUrl)
            .addCallAdapterFactory(ErrorsCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .client(client)
    }


}