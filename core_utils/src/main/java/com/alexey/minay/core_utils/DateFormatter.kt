package com.alexey.minay.core_utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateFormatter {

    fun format1(date: ZonedDateTime): String? {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm z")
        return date.format(formatter)
    }

    fun format2(date: ZonedDateTime): String? {
        val formatter = DateTimeFormatter.ofPattern("MMM")
        return date.format(formatter)
    }

}