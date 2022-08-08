package com.alexey.minay.feature_quotes_chart_impl.domain

import com.alexey.minay.core_utils.Result

interface IQuotesListGateway {
    suspend fun getCurrency(currenciesType: QuotesType.CurrenciesType): Result<ExchangeRateInfo, Nothing>
}