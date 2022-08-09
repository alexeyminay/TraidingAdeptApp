package com.alexey.minay.feature_news_impl

import com.alexey.minay.feature_news_api.INewsFragmentProvider
import com.alexey.minay.feature_news_impl.ui.news.NewsFragment
import com.alexey.minay.feature_news_impl.ui.summary.NewsSummaryFragment
import com.alexey.minay.feature_news_impl.ui.list.NewsListFragment

class NewsFragmentProvider : INewsFragmentProvider {

    override fun provideNewsListFragment() =
        NewsListFragment.newInstance()

    override fun provideNewsFragment() =
        NewsFragment.newInstance()

    override fun provideNewsSummary(newsId: String) =
        NewsSummaryFragment.newInstance(newsId)

}