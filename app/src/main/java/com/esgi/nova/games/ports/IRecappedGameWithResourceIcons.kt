package com.esgi.nova.games.ports

import com.esgi.nova.files.application.model.FileWrapper
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import java.util.*

interface IRecappedGameWithResourceIcons: IGame {
    val resources: List<IFileWrapper<ITotalValueResource>>
    val rounds: Int
}