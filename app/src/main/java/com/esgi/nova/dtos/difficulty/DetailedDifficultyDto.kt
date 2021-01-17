package com.esgi.nova.dtos.difficulty

import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import java.util.*

class DetailedDifficultyDto(
    override val resources: MutableList<IDetailedDifficulty.IStartValueResource>,
    override val id: UUID,
    override val name: String
) : IDetailedDifficulty {
    override fun toString(): String {
        return name
    }
}