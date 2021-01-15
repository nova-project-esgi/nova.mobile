package com.esgi.nova.games.ports

import java.util.*

interface IGameForCreation {
    val username: String
    val difficultyId: UUID
}