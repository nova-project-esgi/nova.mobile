package com.esgi.nova.games.ports

interface IResumedGame {
    val resources: MutableList<ITotalValueResource>
    val duration: Int
    val rounds: Int
}