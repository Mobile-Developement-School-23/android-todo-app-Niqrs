package com.niqr.edit.ui.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Utils for working with [LocalDate]
 */
val tomorrowLocalDate: LocalDate
    get() = LocalDate.now(ZoneOffset.UTC).plusDays(1)

fun LocalDate.toLong(): Long {
    val zoneId = ZoneOffset.UTC
    return atStartOfDay(zoneId).toEpochSecond() * 1000
}

fun LocalDate.toStringDate(): String =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(this)

fun dateFromLong(time: Long): LocalDate =
    Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()
