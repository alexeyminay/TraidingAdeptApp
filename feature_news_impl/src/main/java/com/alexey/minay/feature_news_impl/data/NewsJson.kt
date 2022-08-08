package com.alexey.minay.feature_news_impl.data

data class NewsListResponseJson(
    val news: List<NewsJson>
)

data class NewsJson(
    val title: String,
    val url: String,
    val authors: List<String>,
    val summary: String,
    val bannerImageUrl: String,
    val source: String,
    val sourceDomain: String,
    val topics: List<TopicJson>,
    val tickers: List<TickerJson>,
    val overallSentimentLabel: String
)

data class TopicJson(
    val topic: String,
    val relevantScore: String
)

data class TickerJson(
    val ticker: String,
    val relevantScore: String,
    val tickerSentimentLabel: String
)
