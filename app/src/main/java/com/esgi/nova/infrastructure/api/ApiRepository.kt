package com.esgi.nova.infrastructure.api

import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

open class ApiRepository @Inject constructor(
    private val getUserToken: GetUserToken,
    private val updateUserToken: UpdateUserToken
) {

    private lateinit var genericService: GenericService

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()
        genericService = retrofit.create(GenericService::class.java)
    }

    protected fun <T> Response<Any>.getLocatedContent(): Call<T>? {
        this@getLocatedContent.headers()[HeaderConstants.Location]?.let {
                location -> return@getLocatedContent  this@ApiRepository.genericService.get<T>(location)
        }
        return null
    }

    protected fun Retrofit.Builder.apiBuilder(baseUrl: String = ApiConstants.BaseUrl): Retrofit.Builder {
        val authRequestInterceptor = AuthorizationRequestInterceptor(this@ApiRepository.getUserToken)
        val authResponseInterceptor = TokenRefreshAuthenticator(this@ApiRepository.updateUserToken)
        val httpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(authRequestInterceptor)
            .authenticator(authResponseInterceptor)
            .build()
        return this.baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
    }


}