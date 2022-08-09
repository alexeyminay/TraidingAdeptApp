package com.alexey.minay.feature_news_impl.di

import com.alexey.minay.core_utils.DispatchersProvider
import com.alexey.minay.feature_news_impl.presentation.news.NewsSummaryState
import com.alexey.minay.feature_news_impl.presentation.list.NewsListState
import dagger.Module
import dagger.Provides

@Module
class NewsModule {

    @Provides
    fun provideNewsListState() = NewsListState.default()

    @Provides
    fun provideDispatchersProvider() = DispatchersProvider.default()

    @Provides
    fun provideNewsSummaryState() = NewsSummaryState.default()

}