package com.esgi.nova.infrastructure.api

import com.esgi.nova.NovaApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun Retrofit.Builder.apiBuilder(): Retrofit.Builder {
    val authInterceptor = AuthorizationInterceptor(NovaApplication.getContext())
    val httpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(authInterceptor)
        .build()
    return this.baseUrl(ApiConstants.BaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
}

