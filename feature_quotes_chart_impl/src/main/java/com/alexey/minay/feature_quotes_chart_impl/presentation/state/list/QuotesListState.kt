package com.alexey.minay.feature_quotes_chart_impl.presentation.state.list

data class QuotesListState(
    val items: List<QuotesListItem>
) {
    companion object {
        fun default() = QuotesListState(
            items = emptyList()
        )
    }
}