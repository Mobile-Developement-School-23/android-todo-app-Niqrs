package com.niqr.edit.ui.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Utils for working with Time
 */
val tomorrowLocalDateTime: LocalDateTime
    get() = LocalDate.now(ZoneOffset.UTC).plusDays(1).atStartOfDay()

fun LocalDate.toLong(): Long {
    val zoneId = ZoneOffset.UTC
    return atStartOfDay(zoneId).toEpochSecond() * 1000
}

fun LocalDate.toStringDate(): String =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(this)

fun LocalTime.toStringTime(): String =
    DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(this)

fun dateTimeFromLong(time: Long): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC)
