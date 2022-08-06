package com.alexey.minay.feature_quotes_chart_impl.di

import com.alexey.minay.core_utils.DispatchersProvider
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.QuotesState
import dagger.Module
import dagger.Provides

@Module
class QuotationModule {

    @Provides
    fun provideQuotationState() = QuotesState.default()

    @Provides
    fun provideDispatchersProvider() = DispatchersProvider.default()

}