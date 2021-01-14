package com.esgi.nova.infrastructure.api

import com.esgi.nova.application.NovaApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun Retrofit.Builder.apiBuilder(baseUrl: String = ApiConstants.BaseUrl): Retrofit.Builder {
    val authInterceptor = AuthorizationInterceptor(NovaApplication.getContext())
    val httpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(authInterceptor)
        .build()
    return this.baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
}


