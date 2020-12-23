package com.esgi.nova.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Resource (
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "name") val name: String?
)