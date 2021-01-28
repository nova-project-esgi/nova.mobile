package com.esgi.nova.games.infrastructure.api

import com.esgi.nova.games.exceptions.GameNotFoundException
import com.esgi.nova.infrastructure.api.error_handling.HttpExceptionMapper
import retrofit2.HttpException

class GameExceptionMapper(callArguments: List<String>) : HttpExceptionMapper(callArguments) {
    override fun map(httpException: HttpException): Exception {
        return when (httpException.code()) {
            404 -> GameNotFoundException()
            else -> httpException
        }
    }
}