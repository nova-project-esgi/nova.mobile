package com.esgi.nova.events.ports

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

interface IChoiceResource {
    val resourceId: UUID
    val choiceId: UUID
    val changeValue: Int
}