package com.alexey.minay.feature_quotes_chart_impl.presentation.state.chart

import com.alexey.minay.feature_quotes_chart_impl.domain.Quotation

data class QuotesChartState(
    val quotation: List<Quotation>,
    val type: Type
) {

    enum class Type {
        INIT,
        DATA,
        ERROR
    }

    companion object {
        fun default() = QuotesChartState(
            quotation = emptyList(),
            type = Type.INIT
        )
    }
}