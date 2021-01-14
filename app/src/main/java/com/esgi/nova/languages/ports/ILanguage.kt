package com.esgi.nova.languages.ports

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

interface ILanguage {
    val id: UUID
    val code: String
    val subCode: String?
}