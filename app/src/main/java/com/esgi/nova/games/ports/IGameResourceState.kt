package com.esgi.nova.games.ports

import java.util.*

interface IGameResourceState {
    val resourceId: UUID
    val total: Int
}