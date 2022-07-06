package com.alexey.minay.tradingadeptapp

import com.alexey.minay.tradingadeptapp.domain.Quotation

data class QuotesChartViewState(
    val quotes: List<Quotation>,
) {

    val stepSec = when (quotes.size > 1) {
        true -> quotes[2].dateTime.toEpochSecond() - quotes[1].dateTime.toEpochSecond()
        false -> null
    }

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