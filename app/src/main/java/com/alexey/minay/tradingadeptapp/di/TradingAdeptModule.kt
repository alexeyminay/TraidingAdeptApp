package com.alexey.minay.tradingadeptapp.di

import com.alexey.minay.core_remote.BasicApiFactory
import dagger.Module
import dagger.Provides

@Module
class TradingAdeptModule {

    @Provides
    @AppScope
    fun provideBasicApi() = BasicApiFactory.create()

}