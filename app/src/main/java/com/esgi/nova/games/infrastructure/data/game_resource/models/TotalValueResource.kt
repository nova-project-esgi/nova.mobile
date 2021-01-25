package com.esgi.nova.games.infrastructure.data.game_resource.models

import com.esgi.nova.games.ports.ITotalValueResource
import java.util.*

data class TotalValueResource(
    override val id: UUID,
    override val name: String,
    override val total: Int
) : ITotalValueResource

