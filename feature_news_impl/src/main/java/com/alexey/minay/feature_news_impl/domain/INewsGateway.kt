package com.alexey.minay.feature_news_impl.domain

import com.alexey.minay.core_utils.Result

interface INewsRepository {
    suspend fun getNews(): Result<List<News>, Nothing>
}