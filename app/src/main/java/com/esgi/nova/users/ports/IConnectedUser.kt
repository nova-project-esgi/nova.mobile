package com.esgi.nova.users.ports

import com.esgi.nova.models.Role

interface IConnectedUser {
    val email: String
    val role: Role
    val username: String
    val token: String
}

