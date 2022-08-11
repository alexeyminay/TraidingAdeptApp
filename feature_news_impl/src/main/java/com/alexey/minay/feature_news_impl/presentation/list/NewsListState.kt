package com.alexey.minay.feature_news_impl.presentation.list

import android.os.Parcelable

data class NewsListState(
    val items: List<NewsListItem>,
    val isRefreshing: Boolean,
    val scrollState: Parcelable?
) {
    companion object {
        fun default() = NewsListState(
            items = emptyList(),
            isRefreshing = false,
            scrollState = null
        )
    }
}