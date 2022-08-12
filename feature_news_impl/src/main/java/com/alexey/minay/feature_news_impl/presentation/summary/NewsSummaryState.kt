package com.alexey.minay.feature_news_impl.presentation.summary

import com.alexey.minay.feature_news_impl.domain.News

data class NewsSummaryState(
    val news: News?
) {
    companion object {
        fun default() = NewsSummaryState(
            news = null
        )
    }
}