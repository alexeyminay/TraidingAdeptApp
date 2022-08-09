package com.alexey.minay.feature_news_impl.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsListResponseJson(
    @Json(name = "feed")
    val news: List<NewsJson>
)

@JsonClass(generateAdapter = true)
data class NewsJson(
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "authors")
    val authors: List<String>,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "banner_image")
    val bannerImageUrl: String?,
    @Json(name = "source")
    val source: String,
    @Json(name = "source_domain")
    val sourceDomain: String,
    @Json(name = "topics")
    val topics: List<TopicJson>,
    @Json(name = "ticker_sentiment")
    val tickers: List<TickerJson>,
    @Json(name = "overall_sentiment_label")
    val overallSentimentLabel: String
)

@JsonClass(generateAdapter = true)
data class TopicJson(
    @Json(name = "topic")
    val topic: String,
    @Json(name = "relevance_score")
    val relevantScore: String
)

@JsonClass(generateAdapter = true)
data class TickerJson(
    @Json(name = "ticker")
    val ticker: String,
    @Json(name = "relevance_score")
    val relevantScore: String,
    @Json(name = "ticker_sentiment_label")
    val tickerSentimentLabel: String
)
