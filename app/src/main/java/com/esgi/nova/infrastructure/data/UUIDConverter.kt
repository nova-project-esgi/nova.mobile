package com.esgi.nova.infrastructure.data

import androidx.room.TypeConverter
import java.util.*

object UUIDConverter {
    @TypeConverter
    @JvmStatic
    fun uuidToString(uuid: UUID?): String? {
        return uuid.toString()
    }

    @TypeConverter
    @JvmStatic
    fun stringToUuid(text: String?): UUID? {
        return UUID.fromString(text)
    }
}

