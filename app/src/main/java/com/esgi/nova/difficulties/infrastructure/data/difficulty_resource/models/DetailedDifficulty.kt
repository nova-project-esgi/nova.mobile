package com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.models

import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import java.util.*

data class DetailedDifficulty(
    override val id: UUID,
    override val name: String,
    override val resources: MutableList<IDetailedDifficulty.IStartValueResource> = mutableListOf()
) : IDetailedDifficulty {
}