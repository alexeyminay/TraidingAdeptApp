package com.alexey.minay.feature_quotes_chart_impl.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexey.minay.feature_quotes_chart_impl.databinding.ItemHeaderBinding
import com.alexey.minay.feature_quotes_chart_impl.databinding.ItemQuotationBinding
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.list.QuotesListItem

class QuotesListAdapter
    : ListAdapter<QuotesListItem, QuotesListAdapter.ItemViewHolder>(QuotesDiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            QuotesListItem.QUOTES -> QuotationViewHolder(
                ItemQuotationBinding.inflate(inflater, parent, false)
            )
            QuotesListItem.HEADER -> HeaderViewHolder(
                ItemHeaderBinding.inflate(inflater, parent, false)
            )
            else -> throw RuntimeException("Unknown item type")
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: QuotesListItem)
    }

}

class QuotesDiffUtilsCallback : DiffUtil.ItemCallback<QuotesListItem>() {
    override fun areItemsTheSame(oldItem: QuotesListItem, newItem: QuotesListItem) =
        oldItem.itemType == newItem.itemType

    override fun areContentsTheSame(oldItem: QuotesListItem, newItem: QuotesListItem) =
        oldItem == newItem

}