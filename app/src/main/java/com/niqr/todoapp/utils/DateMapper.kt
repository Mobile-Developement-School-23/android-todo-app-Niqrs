package com.niqr.todoapp.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


fun LocalDate.toLong(): Long {
    val zoneId = ZoneId.systemDefault()
    val epoch: Long = this.atStartOfDay(zoneId).toEpochSecond()
    return epoch
}

fun dateFromLong(time: Long): LocalDate {
    return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun LocalDate.toStringDate(): String =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(this)
