package com.alexey.minay.feature_quotes_chart_impl.di

import com.alexey.minay.feature_quotes_chart_impl.data.gateway.QuotesGateway
import com.alexey.minay.feature_quotes_chart_impl.data.storage.InMemorySupportedQuotesStorage
import com.alexey.minay.feature_quotes_chart_impl.domain.IQuotesChartGateway
import com.alexey.minay.feature_quotes_chart_impl.domain.IQuotesListGateway
import com.alexey.minay.feature_quotes_chart_impl.domain.ISupportedQuotesStorage
import dagger.Binds
import dagger.Module

@Module
interface QuotesChartBinding {

    @Binds
    fun bindQuotesChartGateway(gateway: QuotesGateway): IQuotesChartGateway

    @Binds
    fun bindQuotesListGateway(gateway: QuotesGateway): IQuotesListGateway

    @Binds
    fun bindSupportedQuotesStorage(storage: InMemorySupportedQuotesStorage): ISupportedQuotesStorage

}