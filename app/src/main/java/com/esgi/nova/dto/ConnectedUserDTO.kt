package com.esgi.nova.dto

import com.esgi.nova.models.Role

data class ConnectedUserDTO(val email: String, val role: Role, val username: String, val token: String)
