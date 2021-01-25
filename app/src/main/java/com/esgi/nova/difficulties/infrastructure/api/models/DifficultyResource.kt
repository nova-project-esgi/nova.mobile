package com.esgi.nova.difficulties.infrastructure.api.models

import com.esgi.nova.difficulties.ports.IDifficultyResource
import java.util.*

data class DifficultyResource(
    override val resourceId: UUID,
    override val difficultyId: UUID,
    override val startValue: Int
) : IDifficultyResource