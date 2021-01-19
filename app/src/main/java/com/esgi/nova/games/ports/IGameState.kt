package com.esgi.nova.games.ports

interface IGameState: IGame {
    val resources: List<IGameResourceState>
    val events: List<IGameEventState>
}

