package com.alexey.minay.feature_quotes_chart_impl.ui.list

import com.alexey.minay.feature_quotes_chart_impl.R
import com.alexey.minay.feature_quotes_chart_impl.databinding.ItemQuotationBinding
import com.alexey.minay.feature_quotes_chart_impl.domain.QuotesType
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.list.QuotesListItem

class QuotationViewHolder(
    private val binding: ItemQuotationBinding
) : QuotesListAdapter.ItemViewHolder(binding.root) {

    override fun bind(item: QuotesListItem) = with(binding) {
        if (item !is QuotesListItem.Quotes) return

        title.text = item.title
        subtitle.text = item.subtitle
        value.text = item.value
        when (item.type) {
            is QuotesType.Currencies -> {
                topImage.setImageResource(R.drawable.ic_us)
                bottomImage.setImageResource(R.drawable.ic_rus)
            }
        }
    }

}