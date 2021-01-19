package com.esgi.nova.users.infrastructure.data.models

import com.esgi.nova.users.ports.IUserRecapped
import java.util.*

data class UserRecapped(override val username: String, override val id: UUID) : IUserRecapped {
}