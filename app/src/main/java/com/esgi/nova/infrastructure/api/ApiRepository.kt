package com.esgi.nova.infrastructure.api

import android.content.Context
import com.esgi.nova.infrastructure.api.error_handling.ErrorsCallAdapterFactory
import com.esgi.nova.infrastructure.api.interceptors.NetworkConnectionInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import javax.inject.Inject

open class ApiRepository @Inject constructor(@ApplicationContext protected val context: Context) {
    protected val genericService: GenericService
        get() =
            _genericService ?: apiBuilder()
                .build().create(GenericService::class.java)

    private var _genericService: GenericService? = null


    protected val gsonBuilder: GsonBuilder
        get() = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())

    protected val httpClientBuilder: OkHttpClient.Builder
        get() = OkHttpClient()
            .newBuilder()
            .addInterceptor(NetworkConnectionInterceptor(context))


    protected open fun apiBuilder(baseUrl: String = ApiConstants.BaseUrl): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addCallAdapterFactory(ErrorsCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .client(httpClientBuilder.build())
    }

    protected suspend inline fun <reified T : Any> Response<*>.getLocatedContent(): T? {
        return this@getLocatedContent.headers()[HttpConstants.Headers.Location]?.let { location ->
            val res = this@ApiRepository.genericService.get(location)
            Gson().fromJson(res.body()?.charStream(), T::class.java)
        }
    }

}