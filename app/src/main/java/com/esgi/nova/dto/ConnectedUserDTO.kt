package com.esgi.nova.dto

import com.esgi.nova.models.Role

data class ConnectedUserDTO(
    var email: String,
    var role: Role,
    var username: String,
    var token: String
) {
}