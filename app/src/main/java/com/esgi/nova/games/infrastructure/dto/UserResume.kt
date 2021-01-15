package com.esgi.nova.games.infrastructure.dto

import com.esgi.nova.models.Role
import java.util.*

data class UserResume(val id: UUID, val email: String, val role: Role, val username: String)
