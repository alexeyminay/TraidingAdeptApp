package com.alexey.minay.feature_quotes_chart_impl.domain

import java.time.ZonedDateTime

data class ExchangeRateInfo(
    val type: QuotesType,
    val fromCode: String,
    val fromName: String,
    val toCode: String,
    val toName: String,
    val exchangeRate: Float,
    val lastRefresh: ZonedDateTime
)