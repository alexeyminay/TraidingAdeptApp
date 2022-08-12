package com.alexey.minay.feature_news_impl.presentation.news

import androidx.lifecycle.ViewModel
import com.alexey.minay.core_navigation.Action
import com.alexey.minay.core_navigation.INavigator
import com.alexey.minay.core_ui.SingleStateViewModel
import com.alexey.minay.feature_news_impl.domain.INewsRepository
import com.alexey.minay.feature_news_impl.presentation.list.NewsListState
import com.alexey.minay.feature_news_impl.presentation.summary.NewsSummaryState
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val navigator: INavigator
) : ViewModel() {

    fun openNewsSummary(newsId: String) {
        navigator.perform(Action.OpenNewsSummary(newsId))
    }

}