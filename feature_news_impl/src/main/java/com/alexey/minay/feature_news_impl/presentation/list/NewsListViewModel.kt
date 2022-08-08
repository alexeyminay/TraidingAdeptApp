package com.alexey.minay.feature_news_impl.presentation.list

import androidx.lifecycle.viewModelScope
import com.alexey.minay.core_ui.SingleStateViewModel
import com.alexey.minay.core_utils.Result
import com.alexey.minay.feature_news_impl.domain.INewsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsListViewModel @Inject constructor(
    private val newsRepository: INewsRepository,
    initialState: NewsListState
) : SingleStateViewModel<NewsListState, Nothing>(initialState) {

    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            when (val result = newsRepository.getNews()) {
                is Result.Success -> modify {
                    copy(
                        items = result.data.map { news ->
                            NewsListItem(
                                title = news.title,
                                summary = news.summary,
                                uid = news.uid,
                                thumbnailUrl = news.thimbnailUrl
                            )
                        }
                    )
                }
                is Result.Error -> Unit
            }
        }
    }

}