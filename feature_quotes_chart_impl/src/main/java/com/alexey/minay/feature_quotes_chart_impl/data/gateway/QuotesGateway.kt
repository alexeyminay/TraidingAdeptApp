package com.alexey.minay.feature_quotes_chart_impl.data.gateway

import com.alexey.minay.core_remote.IBasicApi
import com.alexey.minay.core_utils.DispatchersProvider
import com.alexey.minay.core_utils.ZonedDateTimeUtils
import com.alexey.minay.feature_quotes_chart_impl.data.RequestWrapper
import com.alexey.minay.feature_quotes_chart_impl.data.gateway.json.CurrencyResponseJson
import com.alexey.minay.feature_quotes_chart_impl.data.gateway.json.QuotationJson
import com.alexey.minay.feature_quotes_chart_impl.data.gateway.json.QuotesChartResponseJson
import com.alexey.minay.feature_quotes_chart_impl.domain.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuotesGateway @Inject constructor(
    private val basicApi: IBasicApi,
    private val requestWrapper: RequestWrapper,
    private val dispatchersProvider: DispatchersProvider
) : IQuotesChartGateway, IQuotesListGateway {

    override suspend fun getQuotes() = withContext(dispatchersProvider.io) {
        requestWrapper.wrap<List<Quotation>, Nothing> {
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
            result?.timeSeries?.asDomain(result.metadata.timeZone)
        }
    }

    override suspend fun getCurrency(currenciesType: QuotesType.CurrenciesType) =
        withContext(dispatchersProvider.io) {
            requestWrapper.wrap<ExchangeRateInfo, Nothing> {
                val result = basicApi.get(
                    path = "query",
                    resultClass = CurrencyResponseJson::class.java,
                    query = mapOf(
                        "function" to "CURRENCY_EXCHANGE_RATE",
                        "from_currency" to currenciesType.from,
                        "to_currency" to currenciesType.to
                    )
                )
                result?.asDomain(currenciesType)
            }
        }

    private fun Map<String, QuotationJson>.asDomain(timeZone: String) =
        map {
            Quotation(
                dateTime = ZonedDateTimeUtils.fromJson(it.key, timeZone),
                open = it.value.open.toFloat(),
                close = it.value.close.toFloat(),
                high = it.value.high.toFloat(),
                low = it.value.low.toFloat(),
                volume = it.value.volume.toFloat(),
            )
        }

    private fun CurrencyResponseJson.asDomain(currenciesType: QuotesType.CurrenciesType) =
        with(currencyExchangeRate) {
            ExchangeRateInfo(
                type = QuotesType.Currencies(currenciesType),
                fromCode = fromCode,
                fromName = fromName,
                toCode = toCode,
                toName = toName,
                exchangeRate = exchangeRate,
                lastRefresh = ZonedDateTimeUtils.fromJson(lastRefresh, timeZone)
            )
        }

}