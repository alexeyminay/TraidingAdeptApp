package com.alexey.minay.feature_news_impl.presentation.list

import android.os.Parcelable
import androidx.lifecycle.viewModelScope
import com.alexey.minay.core_navigation.Action
import com.alexey.minay.core_navigation.Extras
import com.alexey.minay.core_navigation.INavigator
import com.alexey.minay.core_ui.SingleStateViewModel
import com.alexey.minay.core_utils.Result
import com.alexey.minay.feature_news_impl.domain.INewsRepository
import com.alexey.minay.feature_news_impl.domain.NewsId
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsListViewModel @Inject constructor(
    private val newsRepository: INewsRepository,
    private val navigator: INavigator,
    private val stateHolder: NewsListStateHolder
) : SingleStateViewModel<NewsListState, Nothing>(stateHolder.state) {

    init {
        when (stateHolder.state.type) {
            NewsListState.Type.INIT,
            NewsListState.Type.EMPTY -> fetch()
            else -> Unit
        }
    }

    fun refresh() {
        modify { copy(isRefreshing = true) }
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            when (val result = newsRepository.getAllNews()) {
                is Result.Success -> modify {
                    copy(
                        items = result.data.map { news ->
                            NewsListItem(
                                title = news.title,
                                summary = news.summary,
                                id = news.uid,
                                thumbnailUrl = news.thumbnailUrl,
                                sentimentLabel = news.overallSentimentLabel
                            )
                        },
                        isRefreshing = false,
                        type = when (items.isEmpty()) {
                            true -> NewsListState.Type.EMPTY
                            false -> NewsListState.Type.DATA
                        }
                    )
                }
                is Result.Error -> NewsListState.Type.EMPTY
            }
        }
    }

    fun openNewsSummary(newsId: NewsId, extras: Extras, scrollState: Parcelable?) {
        saveScrollState(scrollState)
        navigator.perform(Action.OpenNewsSummary(newsId.value), extras)
    }

    override fun onCleared() {
        super.onCleared()
        stateHolder.state = state.value
    }

    fun saveScrollState(scrollState: Parcelable?) {
        modify { copy(scrollState = scrollState) }
    }

}