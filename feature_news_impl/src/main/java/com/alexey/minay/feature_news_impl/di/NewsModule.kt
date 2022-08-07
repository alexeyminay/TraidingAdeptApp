package com.alexey.minay.feature_news_impl.di

import com.alexey.minay.core_utils.DispatchersProvider
import com.alexey.minay.feature_news_impl.presentation.NewsListState
import dagger.Module
import dagger.Provides

@Module
class NewsModule {

    @Provides
    fun provideQuotationState() = NewsListState.default()

    @Provides
    fun provideDispatchersProvider() = DispatchersProvider.default()

}