package com.alexey.minay.tradingadeptapp.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
data class QuotesChartResponseJson(
    @Json(name = "Time Series (5min)")
    val timeSeries: Map<ZonedDateTime, QuotationJson>
)

@JsonClass(generateAdapter = true)
data class QuotationJson(
    @Json(name = "1. open")
    val open: String,
    @Json(name = "2. high")
    val high: String,
    @Json(name = "3. low")
    val low: String,
    @Json(name = "4. close")
    val close: String,
    @Json(name = "5. volume")
    val volume: String,
)