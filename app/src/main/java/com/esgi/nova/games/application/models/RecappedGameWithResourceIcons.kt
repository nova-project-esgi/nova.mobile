package com.esgi.nova.games.application.models

import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ports.IRecappedGameWithResourceIcons
import com.esgi.nova.games.ports.ITotalValueResource
import java.util.*

class RecappedGameWithResourceIcons(
    override val id: UUID,
    override val resources: List<IFileWrapper<ITotalValueResource>>,
    override val duration: Int,
    override val rounds: Int
) : IRecappedGameWithResourceIcons {
}