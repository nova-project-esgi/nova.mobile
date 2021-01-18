package com.esgi.nova.games.ports

import com.esgi.nova.files.application.model.FileWrapper
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import java.util.*

interface IResumedGameWithResourceIcons {
    val id: UUID
    val resources: List<IFileWrapper<ITotalValueResource>>
    val duration: Int
    val rounds: Int
}