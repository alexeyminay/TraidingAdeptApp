package com.alexey.minay.feature_news_api

import androidx.fragment.app.Fragment

interface INewsFragmentProvider {
    fun provideNewsListFragment(): Fragment
    fun provideNewsFragment(): Fragment
}