package com.alexey.minay.feature_quotes_chart_impl.domain

import com.alexey.minay.core_utils.Result

interface IQuotesChartGateway {
    suspend fun getQuotes(quotesType: QuotesType): Result<List<Quotation>, Nothing>
}