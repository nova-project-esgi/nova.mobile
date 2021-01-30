package com.esgi.nova.games.ports

import com.esgi.nova.files.infrastructure.ports.IFileWrapper

interface IRecappedGameWithResourceIcons : IGame {
    val resources: List<IFileWrapper<ITotalValueResource>>
    val rounds: Int
}