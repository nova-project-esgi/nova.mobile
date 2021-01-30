package com.esgi.nova.games.ports

interface IRecappedGame : IGame {
    val resources: MutableList<ITotalValueResource>
    val rounds: Int
}

