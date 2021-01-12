package com.esgi.nova.resources.infrastructure.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esgi.nova.difficulties.infrastructure.dto.DifficultyResourceDto
import com.esgi.nova.infrastructure.data.UUIDConverter
import com.esgi.nova.utils.reflectMap
import java.util.*

@Entity(tableName = "resources")
data class Resource(
    @PrimaryKey
    @field:TypeConverters(UUIDConverter::class)
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "name") val name: String?
)
