package com.esgi.nova.difficulties.infrastructure.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

@Entity(tableName = "difficulties")
data class Difficulty(
    @PrimaryKey
    @field:TypeConverters(UUIDConverter::class)
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "name") val name: String
) {
}