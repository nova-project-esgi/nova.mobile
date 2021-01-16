package com.esgi.nova.events.ports

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.esgi.nova.infrastructure.data.IIdEntity
import com.esgi.nova.infrastructure.data.UUIDConverter
import java.util.*

interface IChoiceResource: IIdEntity<UUID> {
    val resourceId: UUID
    val choiceId: UUID
    val changeValue: Int
    override val id: UUID
        get() = choiceId
}