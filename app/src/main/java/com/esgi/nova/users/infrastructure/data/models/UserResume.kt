package com.esgi.nova.users.infrastructure.data.models

import com.esgi.nova.users.ports.IUserResume
import java.util.*

data class UserResume(override val username: String, override val id: UUID) : IUserResume {
}