package com.alexey.minay.feature_quotes_chart_impl.domain

sealed interface QuotesType {
    class Currencies(val type: CurrenciesType) : QuotesType

    enum class CurrenciesType(val from: String, val to: String) {
        USD_RUB("USD", "RUB"),
        EUR_RUB("EUR", "RUB"),
        GBP_RUB("GBP", "RUB")
    }
}