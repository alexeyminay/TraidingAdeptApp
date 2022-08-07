package com.alexey.minay.feature_news_impl

import com.alexey.minay.feature_news_api.INewsFragmentProvider

class NewsFragmentProvider : INewsFragmentProvider {

    override fun provideNewsListFragment() =
        NewsListFragment.newInstance()

    override fun provideNewsFragment() =
        NewsFragment.newInstance()

}