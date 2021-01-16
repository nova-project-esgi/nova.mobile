package com.esgi.nova.games.ports

import java.util.*

interface ITotalValueResource {
    val id: UUID
    val name: String
    val total: Int
}