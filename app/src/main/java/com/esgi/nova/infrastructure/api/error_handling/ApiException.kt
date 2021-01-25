package com.esgi.nova.infrastructure.api.error_handling

/**
 * Exception when communicating with the remote api. Contains http [statusCode].
 */
data class ApiException(val statusCode: String) : Exception()