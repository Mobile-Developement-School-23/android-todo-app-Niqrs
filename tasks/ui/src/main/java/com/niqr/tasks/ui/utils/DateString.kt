package com.niqr.tasks.ui.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Maps [LocalDate] to text representation of date
 */
fun LocalDate.toStringDate(): String =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(this)
