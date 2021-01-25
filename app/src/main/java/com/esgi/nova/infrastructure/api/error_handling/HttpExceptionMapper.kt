package com.esgi.nova.infrastructure.api.error_handling

import retrofit2.HttpException

abstract class HttpExceptionMapper(protected val callArguments: List<String>) {
    abstract fun map(httpException: HttpException): Exception?
}