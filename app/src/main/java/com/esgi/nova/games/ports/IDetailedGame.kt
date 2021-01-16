package com.esgi.nova.games.ports

interface IDetailedGame{
    val resources: MutableList<ITotalValueResource>
    val events: MutableList<ILinkTimeEvent>
    val duration: Int
    val isEnded: Boolean?
}


