package com.alexey.minay.feature_quotes_chart_impl.domain

interface IQuotesChartGateway {
    suspend fun getQuotes(): List<Quotation>?
}