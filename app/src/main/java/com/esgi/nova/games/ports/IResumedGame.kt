package com.esgi.nova.games.ports

import java.util.*

interface IResumedGame {
    val id: UUID
    val resources: MutableList<ITotalValueResource>
    val duration: Int
    val rounds: Int
}