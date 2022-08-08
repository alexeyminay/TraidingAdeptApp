package com.alexey.minay.feature_news_impl.presentation.list

data class NewsListItem(
    val title: String,
    val summary: String,
    val uid: String,
    val thumbnailUrl: String
)