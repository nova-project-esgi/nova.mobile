package com.esgi.nova.languages.infrastructure.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

@Entity(tableName = "languages")
data class Language(
    @PrimaryKey()
    @field:TypeConverters(UUIDConverter::class)
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "sub_code") val subCode: String?

)