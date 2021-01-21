package com.esgi.nova.resources.infrastructure.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esgi.nova.infrastructure.data.UUIDConverter
import com.esgi.nova.resources.ports.IResource
import java.util.*

@Entity(tableName = "resources")
data class ResourceEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    @field:TypeConverters(UUIDConverter::class)
    override var id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "name") override var name: String
): IResource
