package com.alexey.minay.feature_quotes_chart_impl.domain

import com.alexey.minay.feature_quotes_chart_impl.data.Result

interface IQuotesChartGateway {
    suspend fun getQuotes(): Result<List<Quotation>?, Nothing>
}