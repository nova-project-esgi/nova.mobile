package com.esgi.nova.difficulties.ports

import androidx.room.ColumnInfo
import java.util.*

interface IDifficulty {
    val id: UUID
    val name: String
}