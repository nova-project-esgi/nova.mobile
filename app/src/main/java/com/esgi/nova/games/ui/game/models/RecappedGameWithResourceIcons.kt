package com.esgi.nova.games.ui.game.models

import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ports.IRecappedGameWithResourceIcons
import com.esgi.nova.games.ports.ITotalValueResource
import java.util.*

class RecappedGameWithResourceIcons(
    override val resources: List<IFileWrapper<ITotalValueResource>>,
    override val rounds: Int,
    override val id: UUID,
    override val difficultyId: UUID,
    override var duration: Int,
    override var isEnded: Boolean,
    override val userId: UUID
) : IRecappedGameWithResourceIcons {
}