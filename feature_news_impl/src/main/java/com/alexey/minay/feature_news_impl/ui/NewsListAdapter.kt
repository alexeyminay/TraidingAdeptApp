package com.alexey.minay.feature_news_impl.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexey.minay.feature_news_impl.R
import com.alexey.minay.feature_news_impl.databinding.ItemNewsListBinding
import com.alexey.minay.feature_news_impl.presentation.list.NewsListItem
import com.bumptech.glide.Glide

class NewsListAdapter
    : ListAdapter<NewsListItem, NewsListAdapter.NewsViewHolder>(NewsListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(
            binding = ItemNewsListBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class NewsViewHolder(
        private val binding: ItemNewsListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewsListItem) = with(binding) {
            title.text = item.title
            subTitle.text = item.summary
            Glide.with(itemView)
                .load(item.thumbnailUrl)
                .error(R.drawable.ic_default_thumbnail)
                .into(binding.image)
        }

    }

}

class NewsListDiffUtilCallback : DiffUtil.ItemCallback<NewsListItem>() {
    override fun areItemsTheSame(oldItem: NewsListItem, newItem: NewsListItem) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: NewsListItem, newItem: NewsListItem) =
        oldItem == newItem

}