package com.esgi.nova.infrastructure.data

import androidx.room.TypeConverter
import com.esgi.nova.utils.DateConverter
import java.time.LocalDateTime

object DateConverter {
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long): LocalDateTime {
        return DateConverter.fromTimestamp(value)
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: LocalDateTime): Long {
        return DateConverter.dateToTimestamp(date)
    }
}