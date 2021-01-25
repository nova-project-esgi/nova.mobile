package com.esgi.nova.infrastructure.api.exceptions

import java.io.IOException

class NoConnectionException(message: String? = null) : IOException(message) {
}