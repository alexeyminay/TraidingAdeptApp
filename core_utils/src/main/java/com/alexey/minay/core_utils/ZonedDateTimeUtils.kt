package com.alexey.minay.core_utils

import java.time.ZoneId
import java.time.ZonedDateTime

object ZonedDateTimeUtils {

    fun fromJson(date: String, zoneId: String): ZonedDateTime {
        val regex = """(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})""".toRegex()
        val values = regex.find(date)?.groupValues

        return ZonedDateTime.of(
            values?.get(1)?.toInt() ?: 0,
            values?.get(2)?.toInt() ?: 0,
            values?.get(3)?.toInt() ?: 0,
            values?.get(4)?.toInt() ?: 0,
            values?.get(5)?.toInt() ?: 0,
            values?.get(6)?.toInt() ?: 0,
            0, ZoneId.of(zoneId)
        )
    }

}
