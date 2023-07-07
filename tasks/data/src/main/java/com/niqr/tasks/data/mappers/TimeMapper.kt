package com.niqr.tasks.data.mappers

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * Utils for working with [LocalDate] and [LocalDateTime]
 */
fun localDateFromTimestamp(value: Long?): LocalDate? {
    return value?.let { Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDate() }
}

fun LocalDate?.toTimestamp(): Long? {
    return this?.let { it.atStartOfDay(ZoneOffset.UTC).toEpochSecond() * 1000 }
}

fun localDateTimeFromTimestamp(value: Long): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC)
}

fun LocalDateTime.toTimestamp(): Long {
    return this.toEpochSecond(ZoneOffset.UTC) * 1000
}
