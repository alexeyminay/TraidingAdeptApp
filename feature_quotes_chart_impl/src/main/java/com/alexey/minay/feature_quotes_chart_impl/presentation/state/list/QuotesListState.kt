package com.alexey.minay.feature_quotes_chart_impl.presentation.state.list

data class QuotesListState(
    val items: List<QuotesListItem>,
    val type: Type,
    val isRefreshing: Boolean
) {

    enum class Type {
        INIT,
        DATA,
        EMPTY
    }

    companion object {
        fun default() = QuotesListState(
            items = emptyList(),
            type = Type.INIT,
            isRefreshing = false
        )
    }
}