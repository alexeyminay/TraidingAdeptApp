package com.alexey.minay.tradingadeptapp

import com.alexey.minay.tradingadeptapp.domain.Quotation

data class QuotesChartViewState(
    val quotes: List<Quotation>,
) {

    fun findMaxAndMin(pair: MutableMaxMinPair, firstVisibleIndex: Int, lastVisibleIndex: Int) {
        pair.reset()
        quotes.forEachIndexed { index, quotation ->
            if (index > firstVisibleIndex) {
                if (index > lastVisibleIndex) {
                    return
                }

                if (quotation.high > pair.max) {
                    pair.max = quotation.high
                }

                if (quotation.low < pair.min) {
                    pair.min = quotation.low
                }
            }
        }
    }

    companion object {
        fun default() = QuotesChartViewState(
            quotes = emptyList()
        )
    }

}