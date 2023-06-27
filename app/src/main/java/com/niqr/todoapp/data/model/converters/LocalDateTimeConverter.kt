package com.niqr.todoapp.data.model.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneOffset.UTC) }
    }

    @TypeConverter
    fun localDateTimeToTimestamp(time: LocalDateTime?): Long? {
        return time?.let { it.toEpochSecond(ZoneOffset.UTC) * 1000 }
    }
}