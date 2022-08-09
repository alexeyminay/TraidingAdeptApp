package com.alexey.minay.feature_news_impl.data

import com.alexey.minay.core_remote.IBasicApi
import com.alexey.minay.core_utils.DispatchersProvider
import com.alexey.minay.core_utils.RequestWrapper
import com.alexey.minay.feature_news_impl.domain.News
import com.alexey.minay.feature_news_impl.domain.NewsId
import com.alexey.minay.feature_news_impl.domain.Ticker
import com.alexey.minay.feature_news_impl.domain.Topic
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class NewsGateway @Inject constructor(
    private val basicApi: IBasicApi,
    private val requestWrapper: RequestWrapper,
    private val dispatchersProvider: DispatchersProvider
) {

    suspend fun getNews() = withContext(dispatchersProvider.io) {
        requestWrapper.wrap<List<News>, Nothing> {
            val result = basicApi.get(
                path = "query",
                resultClass = NewsListResponseJson::class.java,
                query = mapOf(
                    "function" to "NEWS_SENTIMENT"
                )
            )
            result?.asDomain()
        }
    }

    private fun NewsListResponseJson.asDomain() = news
        .map { newsJson ->
            News(
                title = newsJson.title,
                url = newsJson.url,
                authors = newsJson.authors,
                summary = newsJson.summary,
                thumbnailUrl = newsJson.bannerImageUrl,
                source = newsJson.source,
                sourceDomain = newsJson.sourceDomain,
                topics = newsJson.topics.map { topicJson ->
                    Topic(
                        topic = topicJson.topic,
                        relevantScore = topicJson.relevantScore
                    )
                },
                tickers = newsJson.tickers.map { tickerJson ->
                    Ticker(
                        ticker = tickerJson.ticker,
                        relevantScore = tickerJson.relevantScore,
                        tickerSentimentLabel = tickerJson.tickerSentimentLabel
                    )
                },
                overallSentimentLabel = newsJson.overallSentimentLabel,
                uid = NewsId(UUID.randomUUID().toString())
            )
        }

}