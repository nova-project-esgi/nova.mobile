package com.esgi.nova.games.ports

import java.util.*

interface IGameResourceEdition {
    val resourceId: UUID
    val total: Int
}