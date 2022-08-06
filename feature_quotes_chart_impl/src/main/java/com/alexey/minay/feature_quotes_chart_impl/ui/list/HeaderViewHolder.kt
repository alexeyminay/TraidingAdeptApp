package com.alexey.minay.feature_quotes_chart_impl.ui.list

import com.alexey.minay.core_ui.R
import com.alexey.minay.feature_quotes_chart_impl.databinding.ItemHeaderBinding
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.list.QuotesListItem

class HeaderViewHolder(
    private val binding: ItemHeaderBinding
): QuotesListAdapter.ItemViewHolder(binding.root) {
    override fun bind(item: QuotesListItem) {
        if (item !is QuotesListItem.Header) return

        binding.title.text = when(item.type) {
            QuotesListItem.HeaderType.CURRENCY -> itemView.resources.getText(R.string.currencies)
        }
    }
}