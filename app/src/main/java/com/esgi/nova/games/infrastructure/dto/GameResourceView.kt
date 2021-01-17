package com.esgi.nova.games.infrastructure.dto

import java.util.*

data class GameResourceView(
    val resourceId: UUID,
    val total: Int,
    val name: String)
