package com.esgi.nova.users.ports

import java.util.*

interface IUserResume {
    val username: String
    val id: UUID
}