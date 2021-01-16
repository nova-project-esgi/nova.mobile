package com.esgi.nova.games.infrastructure.dto

import java.util.*

data class LeaderBoardGameView (
    val id: UUID,
    val user: UserResume,
    val duration: Int,
    val difficultyId: UUID,
    val resources: List<GameResourceView>,
    val eventCount: Int
        )
