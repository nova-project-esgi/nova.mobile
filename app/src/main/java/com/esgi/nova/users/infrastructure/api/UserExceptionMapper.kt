package com.esgi.nova.users.infrastructure.api

import com.esgi.nova.infrastructure.api.error_handling.HttpExceptionMapper
import com.esgi.nova.users.exceptions.UserNotFoundException
import retrofit2.HttpException

class UserExceptionMapper(callArguments: List<String>) : HttpExceptionMapper(callArguments) {
    override fun map(httpException: HttpException): Exception {
        return when (httpException.code()) {
            404 -> UserNotFoundException()
            else -> httpException
        }
    }
}