package com.esgi.nova.users.ports

import com.esgi.nova.models.Role
import java.util.*

interface IConnectedUser {
    val id: UUID
    val email: String
    val role: Role
    val username: String
    val token: String
}

