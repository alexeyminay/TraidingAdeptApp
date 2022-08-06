package com.alexey.minay.feature_quotes_chart_impl.presentation.state.list

import com.alexey.minay.feature_quotes_chart_impl.domain.QuotesType

sealed class QuotesListItem(val itemType: Int) {
    class Header(val type: HeaderType) : QuotesListItem(HEADER)
    class Quotes(
        val title: String,
        val subtitle: String,
        val value: String,
        val type: QuotesType
    ) : QuotesListItem(QUOTES)

    enum class HeaderType {
        CURRENCY
    }

    companion object {
        const val HEADER = 0
        const val QUOTES = 1
    }
}