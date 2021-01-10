package com.esgi.nova.difficulties.infrastructure.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Difficulty(
    @PrimaryKey
    val id: UUID,
    @ColumnInfo(name = "name") val name: String
) {
}