package com.alexey.minay.feature_quotes_chart_impl.ui.list

import androidx.core.view.isVisible
import com.alexey.minay.feature_quotes_chart_impl.R
import com.alexey.minay.feature_quotes_chart_impl.databinding.ItemQuotationBinding
import com.alexey.minay.feature_quotes_chart_impl.domain.QuotesType
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.list.QuotesListItem
import com.alexey.minay.core_ui.R as CoreUiR

class QuotationViewHolder(
    private val binding: ItemQuotationBinding
) : QuotesListAdapter.ItemViewHolder(binding.root) {

    override fun bind(item: QuotesListItem) = with(binding) {
        if (item !is QuotesListItem.Quotes) return

        title.text = item.title
        subtitle.text = item.subtitle
        value.text = item.value
        lastRefreshed.text = itemView.resources.getString(CoreUiR.string.lastRefreshed)
            .format(item.lastRefreshed)
        lastRefreshed.isVisible = item.lastRefreshed != null

        when (val type = item.type) {
            is QuotesType.Currencies -> when (type.type) {
                QuotesType.CurrenciesType.USD_RUB -> {
                    topImage.setImageResource(R.drawable.ic_us)
                    bottomImage.setImageResource(R.drawable.ic_rus)
                }
                QuotesType.CurrenciesType.EUR_USD -> {
                    topImage.setImageResource(R.drawable.ic_eur)
                    bottomImage.setImageResource(R.drawable.ic_us)
                }
                QuotesType.CurrenciesType.EUR_RUB -> {
                    topImage.setImageResource(R.drawable.ic_eur)
                    bottomImage.setImageResource(R.drawable.ic_rus)
                }
                QuotesType.CurrenciesType.GBP_USD -> {
                    topImage.setImageResource(R.drawable.ic_gbp)
                    bottomImage.setImageResource(R.drawable.ic_us)
                }
            }
        }
    }

}