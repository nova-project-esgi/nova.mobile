package com.esgi.nova.users.dtos

import com.esgi.nova.models.Role

data class ConnectedUserDto(
    val email: String,
    val role: Role,
    val username: String,
    val token: String
)
