package com.alexey.minay.tradingadeptapp.di

import com.alexey.minay.feature_navigation_impl.ScreenNavigator

interface TradingAdeptDependencies {
    fun provideNavigationScreen(): ScreenNavigator
}