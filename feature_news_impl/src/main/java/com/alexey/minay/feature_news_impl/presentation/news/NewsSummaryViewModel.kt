package com.alexey.minay.feature_news_impl.presentation.news

import com.alexey.minay.core_ui.SingleStateViewModel
import com.alexey.minay.feature_news_impl.domain.INewsRepository
import com.alexey.minay.feature_news_impl.domain.NewsId
import javax.inject.Inject

class NewsSummaryViewModel @Inject constructor(
    private val repository: INewsRepository,
    initialState: NewsSummaryState
) : SingleStateViewModel<NewsSummaryState, Nothing>(initialState) {

    fun fetchNews(newsId: NewsId) {
        val news = repository.getNews(newsId)
        modify {
            copy(
                news = news
            )
        }
    }

}