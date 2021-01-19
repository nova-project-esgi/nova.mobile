package com.esgi.nova.users.application.models

import com.esgi.nova.models.Role
import com.esgi.nova.users.ports.IConnectedUserPassword
import java.util.*

class ConnectedUserPassword(
    override val password: String,
    override val email: String,
    override val role: Role,
    override val username: String,
    override val token: String,
    override val id: UUID
) : IConnectedUserPassword {
}