package com.alexey.minay.feature_quotes_chart_impl

import com.alexey.minay.feature_quotes_chart_impl.domain.Quotation

data class QuotesState(
    val quotes: List<Quotation>
) {
    companion object {
        fun default() = QuotesState(
            quotes = emptyList()
        )
    }
}