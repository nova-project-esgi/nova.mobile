package com.esgi.nova.difficulties.ports

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

interface IDifficultyResource {
    val resourceId: UUID
    val difficultyId: UUID 
    val startValue: Int
}