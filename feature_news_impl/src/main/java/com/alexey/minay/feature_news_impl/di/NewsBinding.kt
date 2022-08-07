package com.alexey.minay.feature_news_impl.di

import androidx.lifecycle.ViewModel
import com.alexey.minay.core_dagger2.ViewModelKey
import com.alexey.minay.feature_news_impl.data.NewsGateway
import com.alexey.minay.feature_news_impl.domain.INewsGateway
import com.alexey.minay.feature_news_impl.presentation.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewsBinding {

    @Binds
    @[IntoMap ViewModelKey(NewsViewModel::class)]
    fun bindQuotesViewModel(quotesChartViewModel: NewsViewModel): ViewModel

    @Binds
    fun bindQuotesChartGateway(gateway: NewsGateway): INewsGateway

}