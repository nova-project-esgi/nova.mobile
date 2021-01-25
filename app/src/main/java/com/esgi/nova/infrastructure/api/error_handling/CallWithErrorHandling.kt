package com.esgi.nova.infrastructure.api.error_handling

import com.esgi.nova.infrastructure.api.exceptions.NoConnectionException
import retrofit2.*
import java.io.IOException


class CallWithErrorHandling(
    private val delegate: Call<Any>
) : Call<Any> by delegate {

    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    callback.onFailure(call, mapExceptionOfCall(call, HttpException(response)))
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                callback.onFailure(call, mapExceptionOfCall(call, t))
            }
        })
    }

    fun mapExceptionOfCall(call: Call<Any>, t: Throwable): Exception {
        val retrofitInvocation = call.request().tag(Invocation::class.java)
        val annotation = retrofitInvocation?.method()?.getAnnotation(ExceptionsMapper::class.java)
        val mapper = try {
            annotation?.value?.java?.constructors?.first()
                ?.newInstance(retrofitInvocation.arguments()) as HttpExceptionMapper
        } catch (e: Exception) {
            null
        }
        return mapToDomainException(t, mapper)
    }

    private fun mapToDomainException(
        remoteException: Throwable,
        httpExceptionsMapper: HttpExceptionMapper? = null
    ): Exception {
        return when (remoteException) {
            is IOException -> NoConnectionException()
            is HttpException -> httpExceptionsMapper?.map(remoteException) ?: ApiException(
                remoteException.code().toString()
            )
            else -> UnexpectedException(remoteException)
        }
    }

    override fun clone() = CallWithErrorHandling(delegate.clone())
}


//class LogUserExceptionMapper(arguments: List<String>) : HttpExceptionMapper(arguments) {
//
//    override fun map(httpException: HttpException): Exception? {
//        return if (httpException.code() == 404) {
//            UserNotFoundException()
//        } else {
//            null
//        }
//    }
//}