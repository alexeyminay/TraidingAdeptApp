package com.alexey.minay.feature_news_impl.presentation

import com.alexey.minay.core_ui.SingleStateViewModel
import javax.inject.Inject

class NewsListViewModel @Inject constructor(
    initialState: NewsListState
): SingleStateViewModel<NewsListState, Nothing>(initialState) {
}