package com.alexey.minay.feature_quotes_chart_impl.data.storage

import com.alexey.minay.feature_quotes_chart_impl.domain.ISupportedQuotesStorage
import com.alexey.minay.feature_quotes_chart_impl.domain.QuotesType
import javax.inject.Inject

class InMemorySupportedQuotesStorage @Inject constructor() : ISupportedQuotesStorage {

    override fun getCurrencies(): List<QuotesType.CurrenciesType> {
        return listOf(
            QuotesType.CurrenciesType.USD_RUB,
//            QuotesType.CurrenciesType.EUR_RUB,
//            QuotesType.CurrenciesType.EUR_USD,
//            QuotesType.CurrenciesType.GBP_USD,
        )
    }

}