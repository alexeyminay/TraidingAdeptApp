package com.alexey.minay.feature_quotes_chart_impl.presentation.state

import com.alexey.minay.feature_quotes_chart_impl.presentation.state.chart.QuotesChartState
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.list.QuotesListState

data class QuotesState(
    val quotesChartState: QuotesChartState,
    val listState: QuotesListState
) {
    companion object {
        fun default() = QuotesState(
            quotesChartState = QuotesChartState.default(),
            listState = QuotesListState.default()
        )
    }
}