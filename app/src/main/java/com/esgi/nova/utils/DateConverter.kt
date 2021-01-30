package com.esgi.nova.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

object DateConverter {
    fun fromTimestamp(value: Long): LocalDateTime {
        return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(value),
            TimeZone.getDefault().toZoneId()
        )
    }

    fun dateToTimestamp(date: LocalDateTime): Long {
        return ZonedDateTime.of(
            date,
            ZoneId.systemDefault()
        ).toInstant().toEpochMilli()
    }
}