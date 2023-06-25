package com.niqr.todoapp.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

val tomorrowLocalDate: LocalDate
    get() = LocalDate.now(ZoneOffset.UTC).plusDays(1)

fun LocalDate.toLong(): Long {
    val zoneId = ZoneOffset.UTC
    return atStartOfDay(zoneId).toEpochSecond() * 1000
}

fun dateFromLong(time: Long): LocalDate =
    Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()

fun LocalDate.toStringDate(): String =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(this)

fun LocalDate.toShortStringDate(): String =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(this)
