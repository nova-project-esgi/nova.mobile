package com.esgi.nova.games.infrastructure.data.game.models

import com.esgi.nova.games.ports.IDetailedGame
import com.esgi.nova.games.ports.ILinkTimeEvent
import com.esgi.nova.games.ports.ITotalValueResource

data class DetailedGame(
    override val resources: MutableList<ITotalValueResource>,
    override val events: MutableList<ILinkTimeEvent>,
    override val duration: Int,
    override val isEnded: Boolean?
) : IDetailedGame