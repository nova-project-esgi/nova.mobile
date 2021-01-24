package com.esgi.nova.difficulties.infrastructure.api.models

import com.esgi.nova.difficulties.ports.IDifficultyResource
import com.esgi.nova.difficulties.ports.IResumedDifficulty
import java.util.*

data class ResumedDifficulty(
    override val resources: MutableList<IDifficultyResource>,
    override val id: UUID,
    override val name: String, override val rank: Int
): IResumedDifficulty {
}