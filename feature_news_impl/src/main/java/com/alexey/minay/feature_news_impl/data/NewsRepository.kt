package com.alexey.minay.feature_news_impl.data

import com.alexey.minay.core_dagger2.FeatureScope
import com.alexey.minay.core_utils.Result
import com.alexey.minay.feature_news_impl.domain.INewsRepository
import com.alexey.minay.feature_news_impl.domain.News
import com.alexey.minay.feature_news_impl.domain.NewsId
import javax.inject.Inject

@FeatureScope
class NewsRepository @Inject constructor(
    private val gateway: NewsGateway
) : INewsRepository {

    private var mNews = emptyList<News>()

    override suspend fun getAllNews(): Result<List<News>, Nothing> {
        val result = gateway.getNews()
        when (result) {
            is Result.Success -> {
                mNews = result.data
            }
            is Result.Error -> Unit
        }
        return result
    }

    override fun getNews(newsId: NewsId): News? {
        return mNews.firstOrNull { it.uid == newsId }
    }

}