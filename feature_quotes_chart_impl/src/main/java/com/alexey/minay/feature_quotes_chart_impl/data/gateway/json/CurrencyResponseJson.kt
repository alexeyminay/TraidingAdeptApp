package com.alexey.minay.feature_quotes_chart_impl.data.gateway.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
data class CurrencyResponseJson(
    @Json(name = "Realtime Currency Exchange Rate")
    val currencyExchangeRate: CurrencyExchangeRateJson
)

@JsonClass(generateAdapter = true)
data class CurrencyExchangeRateJson(
    @Json(name = "1. From_Currency Code")
    val fromCode: String,
    @Json(name = "2. From_Currency Name")
    val fromName: String,
    @Json(name = "3. To_Currency Code")
    val toCode: String,
    @Json(name = "4. To_Currency Name")
    val toName: String,
    @Json(name = "5. Exchange Rate")
    val exchangeRate: Float,
    @Json(name = "6. Last Refreshed")
    val lastRefresh: String,
    @Json(name = "7. Time Zone")
    val timeZone: String
)