package com.alexey.minay.feature_quotes_chart_impl.domain

import com.alexey.minay.feature_quotes_chart_impl.data.Result

interface IQuotesListGateway {
    suspend fun getCurrency(currenciesType: QuotesType.CurrenciesType): Result<ExchangeRateInfo, Nothing>
}