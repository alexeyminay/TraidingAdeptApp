package com.alexey.minay.feature_quotes_chart_impl.di

import com.alexey.minay.core_remote.IBasicApi

interface QuotesChartDependencies {
    fun provideBasicApi(): IBasicApi
}