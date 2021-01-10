package com.esgi.nova.events.infrastructure.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Event (
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "isDaily") val isDaily: Boolean?,
    @ColumnInfo(name = "isActive") val isActive: Boolean?,
    @ColumnInfo(name = "description") val description: String?
)