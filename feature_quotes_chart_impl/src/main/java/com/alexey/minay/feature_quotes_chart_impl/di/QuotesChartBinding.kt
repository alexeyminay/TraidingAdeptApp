package com.alexey.minay.feature_quotes_chart_impl.di

import androidx.lifecycle.ViewModel
import com.alexey.minay.core_dagger2.ViewModelKey
import com.alexey.minay.feature_quotes_chart_impl.data.gateway.QuotesGateway
import com.alexey.minay.feature_quotes_chart_impl.data.storage.InMemorySupportedQuotesStorage
import com.alexey.minay.feature_quotes_chart_impl.domain.IQuotesChartGateway
import com.alexey.minay.feature_quotes_chart_impl.domain.IQuotesListGateway
import com.alexey.minay.feature_quotes_chart_impl.domain.ISupportedQuotesStorage
import com.alexey.minay.feature_quotes_chart_impl.presentation.QuotesChartViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface QuotesChartBinding {

    @Binds
    @[IntoMap ViewModelKey(QuotesChartViewModel::class)]
    fun bindQuotesViewModel(quotesChartViewModel: QuotesChartViewModel): ViewModel

    @Binds
    fun bindQuotesChartGateway(gateway: QuotesGateway): IQuotesChartGateway

    @Binds
    fun bindQuotesListGateway(gateway: QuotesGateway): IQuotesListGateway

    @Binds
    fun bindSupportedQuotesStorage(storage: InMemorySupportedQuotesStorage): ISupportedQuotesStorage

}