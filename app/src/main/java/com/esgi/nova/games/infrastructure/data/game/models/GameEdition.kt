package com.esgi.nova.games.infrastructure.data.game.models

import com.esgi.nova.games.ports.IGameEdition
import com.esgi.nova.games.ports.IGameEventEdition
import com.esgi.nova.games.ports.IGameResourceEdition

data class GameEdition(
    override val resources: MutableList<IGameResourceEdition>,
    override val events: MutableList<IGameEventEdition>,
    override val duration: Int,
    override val isEnded: Boolean?
) : IGameEdition