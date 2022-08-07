package com.alexey.minay.feature_news_impl.di

import com.alexey.minay.core_remote.IBasicApi

interface NewsDependencies {
    fun provideBasicApi(): IBasicApi
}