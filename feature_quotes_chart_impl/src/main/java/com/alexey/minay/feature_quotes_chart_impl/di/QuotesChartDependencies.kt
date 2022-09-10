package com.alexey.minay.feature_quotes_chart_impl.di

import com.alexey.minay.core_navigation.INavigator
import com.alexey.minay.core_remote.IBasicApi
import com.alexey.minay.core_utils.RequestWrapper

interface QuotesChartDependencies {
    fun provideBasicApi(): IBasicApi
    fun provideRequestWrapper(): RequestWrapper
    fun provideNavigator(): INavigator
}