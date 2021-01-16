package com.esgi.nova.games.infrastructure.data.game.models

import com.esgi.nova.games.infrastructure.api.models.GameEventEdition
import com.esgi.nova.games.infrastructure.api.models.GameResourceEdition
import com.esgi.nova.games.ports.*

data class DetailedGame(
    override val resources: MutableList<ITotalValueResource>,
    override val events: MutableList<ILinkTimeEvent>,
    override val duration: Int,
    override val isEnded: Boolean?
): IDetailedGame