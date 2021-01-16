package com.esgi.nova.games.ports

interface IGameEdition{
    val resources: MutableList<IGameResourceEdition>
    val events: MutableList<IGameEventEdition>
    val duration: Int
    val isEnded: Boolean?
}