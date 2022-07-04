package com.alexey.minay.tradingadeptapp.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.ZoneId
import java.time.ZonedDateTime

class ZonedDateTimeAdapter {

    @ToJson()
    fun toJson(date: ZonedDateTime): String {
        return ""
    }

    @FromJson
    fun fromJson(date: String): ZonedDateTime {
        val regex = """(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})""".toRegex()
        val values = regex.find(date)?.groupValues

        return ZonedDateTime.of(
            values?.get(1)?.toInt() ?: 0,
            values?.get(2)?.toInt() ?: 0,
            values?.get(3)?.toInt() ?: 0,
            values?.get(4)?.toInt() ?: 0,
            values?.get(5)?.toInt() ?: 0,
            values?.get(6)?.toInt() ?: 0,
            0, ZoneId.systemDefault()
        )
    }

}
