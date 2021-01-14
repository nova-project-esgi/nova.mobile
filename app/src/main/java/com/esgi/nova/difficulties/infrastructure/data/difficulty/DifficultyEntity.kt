package com.esgi.nova.difficulties.infrastructure.data.difficulty

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esgi.nova.difficulties.ports.IDifficulty
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

@Entity(tableName = "difficulties")
data class DifficultyEntity(
    @PrimaryKey
    @field:TypeConverters(UUIDConverter::class)
    override val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "name") override val name: String
): IDifficulty {
}