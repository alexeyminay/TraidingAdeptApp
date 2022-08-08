package com.alexey.minay.feature_news_impl.domain

data class News(
    val title: String,
    val url: String,
    val authors: List<String>,
    val summary: String,
    val bannerImageUrl: String,
    val source: String,
    val sourceDomain: String,
    val topics: List<Topic>,
    val tickers: List<Ticker>,
    val overallSentimentLabel: String
)