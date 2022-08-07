package com.alexey.minay.feature_quotes_chart_impl.data.gateway.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuotesChartResponseJson(
    @Json(name = "Time Series (5min)")
    val timeSeries: Map<String, QuotationJson>,
    @Json(name = "Meta Data")
    val metadata: MetaDataJson
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

@JsonClass(generateAdapter = true)
data class MetaDataJson(
    @Json(name = "7. Time Zone")
    val timeZone: String
)