package com.esgi.nova.resources.ports

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

interface IResource {
    val id: UUID
     val name: String?
}