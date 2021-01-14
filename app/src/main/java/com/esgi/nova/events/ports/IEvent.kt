package com.esgi.nova.events.ports

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

interface IEvent {
    val id: UUID
    val description: String
    val title: String
}