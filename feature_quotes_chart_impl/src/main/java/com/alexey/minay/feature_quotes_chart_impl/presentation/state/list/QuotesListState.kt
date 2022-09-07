package com.alexey.minay.feature_quotes_chart_impl.presentation.state.list

import com.alexey.minay.core_utils.indexOfFirstInstanceOrNull

data class QuotesListState(
    val items: List<QuotesListItem>,
    val type: Type,
    val isRefreshing: Boolean
) {

    val selectedIndex = items.indexOfFirstInstanceOrNull(QuotesListItem.Quotes::isSelected)
    val selected = items.firstOrNull { it is QuotesListItem.Quotes && it.isSelected }
            as? QuotesListItem.Quotes

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