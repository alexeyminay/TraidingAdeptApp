package com.alexey.minay.feature_news_impl.ui.summary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexey.minay.feature_news_impl.databinding.ItemTickerBinding
import com.alexey.minay.feature_news_impl.domain.Ticker
import com.alexey.minay.feature_news_impl.ui.SentimentLabelMapper

class TickerAdapter :
    ListAdapter<Ticker, TickerAdapter.TickerViewHolder>(TickerDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TickerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TickerViewHolder(
            binding = ItemTickerBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TickerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class TickerViewHolder(
        private val binding: ItemTickerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Ticker) = with(binding) {
            ticker.text = item.ticker
            SentimentLabelMapper.map(
                sentimentLabel = item.tickerSentimentLabel,
                icon = icon,
                subIcon = subIcon
            )
        }

    }

}

class TickerDiffUtilCallback : DiffUtil.ItemCallback<Ticker>() {
    override fun areItemsTheSame(oldItem: Ticker, newItem: Ticker) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Ticker, newItem: Ticker) =
        oldItem == newItem

}