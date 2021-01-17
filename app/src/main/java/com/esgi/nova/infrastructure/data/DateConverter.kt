package com.esgi.nova.infrastructure.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.esgi.nova.utils.DateConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

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