package com.alexey.minay.feature_news_impl.presentation

import com.alexey.minay.core_ui.SingleStateViewModel
import com.alexey.minay.feature_news_impl.domain.INewsRepository
import com.alexey.minay.feature_news_impl.presentation.list.NewsListState
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsRepository: INewsRepository,
    initialState: NewsListState
) : SingleStateViewModel<NewsListState, Nothing>(initialState) {


}