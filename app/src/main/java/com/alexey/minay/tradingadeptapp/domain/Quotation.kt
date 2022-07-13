package com.alexey.minay.tradingadeptapp.domain

import java.io.Serializable
import java.time.ZonedDateTime

data class Quotation(
    val dateTime: ZonedDateTime,
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float,
    val volume: Float,
) : Serializable