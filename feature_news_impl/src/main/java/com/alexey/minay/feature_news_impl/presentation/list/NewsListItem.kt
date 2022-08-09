package com.alexey.minay.feature_news_impl.presentation.list

import com.alexey.minay.feature_news_impl.domain.NewsId

data class NewsListItem(
    val title: String,
    val summary: String,
    val id: NewsId,
    val thumbnailUrl: String?
)