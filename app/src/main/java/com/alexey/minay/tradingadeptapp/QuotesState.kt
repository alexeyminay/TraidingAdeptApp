package com.alexey.minay.tradingadeptapp

import com.alexey.minay.tradingadeptapp.domain.Quotation

data class QuotesState(
    val quotes: List<Quotation>
) {
    companion object {
        fun default() = QuotesState(
            quotes = emptyList()
        )
    }
}