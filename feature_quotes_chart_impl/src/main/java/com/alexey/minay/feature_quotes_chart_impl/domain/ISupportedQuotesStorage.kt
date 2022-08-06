package com.alexey.minay.feature_quotes_chart_impl.domain

interface ISupportedQuotesStorage {
    fun getCurrencies(): List<QuotesType.CurrenciesType>
}