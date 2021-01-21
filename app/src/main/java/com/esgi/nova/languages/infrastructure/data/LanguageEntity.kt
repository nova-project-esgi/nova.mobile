package com.esgi.nova.languages.infrastructure.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esgi.nova.infrastructure.data.IIdEntity
import com.esgi.nova.infrastructure.data.UUIDConverter
import com.esgi.nova.languages.ports.IAppLanguage
import java.util.*

@Entity(tableName = "languages")
data class LanguageEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey()
    @field:TypeConverters(UUIDConverter::class)
    override var id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "code") override var code: String,
    @ColumnInfo(name = "sub_code") override var subCode: String?,
    @ColumnInfo(name = "is_selected") override var isSelected: Boolean = false
): IAppLanguage {
}
