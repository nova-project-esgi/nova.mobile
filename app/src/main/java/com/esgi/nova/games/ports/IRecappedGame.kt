package com.esgi.nova.games.ports

import java.util.*

interface IRecappedGame: IGame {
    val resources: MutableList<ITotalValueResource>
    val rounds: Int
}

