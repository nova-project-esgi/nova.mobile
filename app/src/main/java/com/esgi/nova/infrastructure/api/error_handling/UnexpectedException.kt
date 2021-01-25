package com.esgi.nova.infrastructure.api.error_handling

/**
 * Not handled unexpected exception
 */
class UnexpectedException(cause: Throwable) : Exception(cause)