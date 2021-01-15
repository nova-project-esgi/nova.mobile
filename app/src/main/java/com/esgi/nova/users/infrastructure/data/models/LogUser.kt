package com.esgi.nova.users.infrastructure.data.models

import com.esgi.nova.users.ports.ILogUser

data class LogUser(override val username: String, override val password: String): ILogUser {
}