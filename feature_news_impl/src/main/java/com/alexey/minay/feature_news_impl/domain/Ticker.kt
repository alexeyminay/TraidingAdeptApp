package com.alexey.minay.feature_news_impl.domain

data class Ticker(
    val ticker: String,
    val relevantScore: String,
    val tickerSentimentLabel: String
)