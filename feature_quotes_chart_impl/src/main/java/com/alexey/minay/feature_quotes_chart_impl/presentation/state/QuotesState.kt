package com.alexey.minay.feature_quotes_chart_impl.presentation.state

data class QuotesState(
    val quotesChartState: QuotesChartState
) {
    companion object {
        fun default() = QuotesState(
            quotesChartState = QuotesChartState.default()
        )
    }
}