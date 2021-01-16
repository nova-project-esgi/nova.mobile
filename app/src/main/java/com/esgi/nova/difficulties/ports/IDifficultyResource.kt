package com.esgi.nova.difficulties.ports

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.esgi.nova.infrastructure.data.IIdEntity
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

interface IDifficultyResource: IIdEntity<UUID> {
    val resourceId: UUID
    val difficultyId: UUID 
    val startValue: Int
    override val id: UUID
        get() = difficultyId
}