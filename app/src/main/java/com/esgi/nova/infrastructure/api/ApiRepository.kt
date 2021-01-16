package com.esgi.nova.infrastructure.api

import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import com.esgi.nova.utils.reflectMapNotNull
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.LinkedTreeMap
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.time.LocalDateTime
import javax.inject.Inject

open class ApiRepository @Inject constructor(
    private val getUserToken: GetUserToken,
    private val updateUserToken: UpdateUserToken
) {

    lateinit var genericService: GenericService

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()
        genericService = retrofit.create(GenericService::class.java)
    }

    inline protected fun <reified T : Any> Response<*>.getLocatedContent(): T? {
        this@getLocatedContent.headers()[HeaderConstants.Location]?.let { location ->
            return try{
                val req = this@ApiRepository.genericService.get(location).execute()
                Gson().fromJson(req.body()?.string(), T::class.java)
            } catch (e: Exception){
                null
            }
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
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .create()
        return this.baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
    }


}