package com.alexey.minay.feature_news_impl.di

import com.alexey.minay.core_navigation.INavigator
import com.alexey.minay.core_remote.IBasicApi
import com.alexey.minay.core_utils.RequestWrapper

interface NewsDependencies {
    fun provideBasicApi(): IBasicApi
    fun provideRequestWrapper(): RequestWrapper
    fun provideNavigator(): INavigator
}