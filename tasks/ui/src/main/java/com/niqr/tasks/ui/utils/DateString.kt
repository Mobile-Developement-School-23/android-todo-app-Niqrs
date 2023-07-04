package com.niqr.tasks.ui.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun LocalDate.toStringDate(): String =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(this)
