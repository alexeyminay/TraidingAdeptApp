package com.alexey.minay.feature_quotes_chart_impl

import com.alexey.minay.feature_quotes_chart_impl.domain.Quotation
import java.io.Serializable

data class QuotesChartViewState(
    val quotes: List<Quotation>,
) : Serializable {

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