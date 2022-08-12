package com.alexey.minay.feature_news_api

import androidx.fragment.app.Fragment

interface INewsFragmentProvider {
    fun provideNewsListFragment(): Fragment
    fun provideNewsSummary(newsId: String): Fragment
    fun provideNewsFragment(newsUrl: String, newsId: String): Fragment
}