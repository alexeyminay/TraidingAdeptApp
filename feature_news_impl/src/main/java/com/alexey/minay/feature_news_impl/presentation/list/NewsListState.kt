package com.alexey.minay.feature_news_impl.presentation.list

data class NewsListState(
    val items: List<NewsListItem>
) {
    companion object {
        fun default() = NewsListState(
            items = emptyList()
        )
    }
}