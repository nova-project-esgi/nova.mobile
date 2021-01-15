package com.esgi.nova.infrastructure.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

object DateConverter {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long): LocalDateTime {
        return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(value),
            TimeZone.getDefault().toZoneId());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: LocalDateTime): Long {
        return ZonedDateTime.of(
            date,
            ZoneId.systemDefault()
        ).toInstant().toEpochMilli()
    }
}