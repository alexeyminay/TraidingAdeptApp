package com.alexey.minay.feature_quotes_chart_impl.domain

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