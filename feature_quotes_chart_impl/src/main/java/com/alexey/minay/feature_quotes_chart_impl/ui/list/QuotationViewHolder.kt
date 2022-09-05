package com.alexey.minay.feature_quotes_chart_impl.ui.list

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.alexey.minay.feature_quotes_chart_impl.databinding.ItemQuotationBinding
import com.alexey.minay.feature_quotes_chart_impl.domain.QuotesType
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.list.QuotesListItem
import com.alexey.minay.core_ui.R as CoreUiR

class QuotationViewHolder(
    private val binding: ItemQuotationBinding,
    private val onSelect: (QuotesType) -> Unit
) : QuotesListAdapter.ItemViewHolder(binding.root) {

    override fun bind(item: QuotesListItem) = with(binding) {
        if (item !is QuotesListItem.Quotes) return

        title.text = item.title
        subtitle.text = item.subtitle
        value.text = item.value
        lastRefreshed.text = itemView.resources.getString(CoreUiR.string.lastRefreshed)
            .format(item.lastRefreshed)
        lastRefreshed.isVisible = item.lastRefreshed != null

        setIcons(item)
        setSelection(item)
    }

    private fun setIcons(item: QuotesListItem.Quotes) = with(binding) {
        when (val type = item.type) {
            is QuotesType.Currencies -> when (type.type) {
                QuotesType.CurrenciesType.USD_RUB -> {
                    iconGroup.topImage.setImageResource(CoreUiR.drawable.ic_us)
                    iconGroup.bottomImage.setImageResource(CoreUiR.drawable.ic_rus)
                }
                QuotesType.CurrenciesType.GBP_RUB -> {
                    iconGroup.topImage.setImageResource(CoreUiR.drawable.ic_gbp)
                    iconGroup.bottomImage.setImageResource(CoreUiR.drawable.ic_rus)
                }
                QuotesType.CurrenciesType.EUR_RUB -> {
                    iconGroup.topImage.setImageResource(CoreUiR.drawable.ic_eur)
                    iconGroup.bottomImage.setImageResource(CoreUiR.drawable.ic_rus)
                }
            }
        }
    }

    private fun setSelection(item: QuotesListItem.Quotes) = with(binding) {
        clickableArea.setOnClickListener { onSelect(item.type) }
        root.background = when {
            item.isSelected -> ContextCompat.getDrawable(
                itemView.context,
                CoreUiR.color.selectedItem
            )
            else -> null
        }
    }

}