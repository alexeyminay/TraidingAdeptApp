package com.alexey.minay.feature_news_impl.di

import androidx.lifecycle.ViewModel
import com.alexey.minay.core_dagger2.ViewModelKey
import com.alexey.minay.feature_news_impl.data.NewsRepository
import com.alexey.minay.feature_news_impl.domain.INewsRepository
import com.alexey.minay.feature_news_impl.presentation.NewsViewModel
import com.alexey.minay.feature_news_impl.presentation.list.NewsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewsBinding {

    @Binds
    @[IntoMap ViewModelKey(NewsViewModel::class)]
    fun bindNewsViewModel(quotesChartViewModel: NewsViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(NewsListViewModel::class)]
    fun bindNewsListViewModel(quotesChartViewModel: NewsListViewModel): ViewModel

    @Binds
    fun bindQuotesChartGRepository(repository: NewsRepository): INewsRepository

}