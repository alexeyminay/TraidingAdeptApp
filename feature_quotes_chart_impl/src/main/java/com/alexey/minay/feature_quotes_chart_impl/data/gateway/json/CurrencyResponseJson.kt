package com.alexey.minay.feature_quotes_chart_impl.data.gateway.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyResponseJson(
    @Json(name = "Valute")
    val currencyExchangeRate: Map<String, CurrencyExchangeRateJson>,
    @Json(name = "Date")
    val date: String
)

@JsonClass(generateAdapter = true)
data class CurrencyExchangeRateJson(
    @Json(name = "CharCode")
    val code: String,
    @Json(name = "Name")
    val name: String,
    @Json(name = "Value")
    val value: Float,
    @Json(name = "Previous")
    val previous: Float,
)