package com.alexey.minay.feature_news_impl.domain

import com.alexey.minay.core_utils.Result

interface INewsRepository {
    suspend fun getAllNews(): Result<List<News>, Nothing>
    fun getNews(newsId: NewsId): News?
}