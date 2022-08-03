package com.alexey.minay.feature_quotes_chart_impl.data

import com.alexey.minay.core_remote.IBasicApi
import com.alexey.minay.feature_quotes_chart_impl.domain.IQuotesChartGateway
import com.alexey.minay.feature_quotes_chart_impl.domain.Quotation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import javax.inject.Inject

class QuotesChartGateway @Inject constructor(
    private val basicApi: IBasicApi,
    private val requestWrapper: RequestWrapper
) : IQuotesChartGateway {

    override suspend fun getQuotes() = withContext(Dispatchers.IO) {
        requestWrapper.wrap<List<Quotation>?, Nothing> {
            val result = basicApi.get(
                path = "query",
                resultClass = QuotesChartResponseJson::class.java,
                query = mapOf(
                    "function" to "TIME_SERIES_INTRADAY",
                    "outputsize" to "full",
                    "symbol" to "IBM",
                    "interval" to "5min",
                )
            )
            result?.timeSeries?.asDomain()
        }
    }

    private fun Map<ZonedDateTime, QuotationJson>.asDomain() =
        map {
            Quotation(
                dateTime = it.key,
                open = it.value.open.toFloat(),
                close = it.value.close.toFloat(),
                high = it.value.high.toFloat(),
                low = it.value.low.toFloat(),
                volume = it.value.volume.toFloat(),
            )
        }

}