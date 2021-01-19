package com.esgi.nova.games.ports

interface IGameState: IGame {
    val resourceStates: List<IGameResourceState>
    val eventStates: List<IGameEventState>
}

