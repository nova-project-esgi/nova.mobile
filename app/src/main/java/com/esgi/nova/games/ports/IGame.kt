package com.esgi.nova.games.ports

import java.util.*

interface IGame {
    val id: UUID
    val difficultyId: UUID
}