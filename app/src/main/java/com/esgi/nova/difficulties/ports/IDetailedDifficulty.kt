package com.esgi.nova.difficulties.ports

import java.util.*

interface IDetailedDifficulty: IDifficulty {
    val resources: MutableList<IStartValueResource>
    interface IStartValueResource {
        val id: UUID
        val name: String
        val startValue: Int
    }
}

