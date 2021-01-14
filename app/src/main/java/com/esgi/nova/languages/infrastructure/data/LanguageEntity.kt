package com.esgi.nova.languages.infrastructure.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esgi.nova.infrastructure.data.UUIDConverter
import com.esgi.nova.languages.ports.ILanguage
import java.util.*

@Entity(tableName = "languages")
data class LanguageEntity(
    @PrimaryKey()
    @field:TypeConverters(UUIDConverter::class)
    override val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "code") override val code: String,
    @ColumnInfo(name = "sub_code") override val subCode: String?

): ILanguage