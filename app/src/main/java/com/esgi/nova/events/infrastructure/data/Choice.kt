package com.esgi.nova.events.infrastructure.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

@Entity(tableName = "choices")
data class Choice(
    @PrimaryKey
    @field:TypeConverters(UUIDConverter::class)
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?
) {}