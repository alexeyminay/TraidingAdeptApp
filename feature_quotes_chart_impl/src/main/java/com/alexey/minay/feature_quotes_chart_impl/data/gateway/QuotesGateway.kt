package com.alexey.minay.feature_quotes_chart_impl.data.gateway

import com.alexey.minay.core_remote.IBasicApi
import com.alexey.minay.core_utils.DispatchersProvider
import com.alexey.minay.core_utils.RequestWrapper
import com.alexey.minay.core_utils.Result
import com.alexey.minay.core_utils.ZonedDateTimeUtils
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

    override suspend fun getQuotes(quotesType: QuotesType) = withContext(dispatchersProvider.io) {
        when (quotesType) {
            is QuotesType.Currencies -> requestWrapper.wrap<List<Quotation>, Nothing> {
                val result = basicApi.get(
                    path = "query",
                    resultClass = QuotesChartResponseJson::class.java,
                    query = mapOf(
                        "function" to "FX_DAILY",
                        "from_symbol" to quotesType.type.from,
                        "to_symbol" to quotesType.type.to,
                        "outputsize" to "full"
                    )
                )
                result?.timeSeries?.asDomain(result.metadata.timeZone)
            }
        }

    }

    override suspend fun getCurrency(
        currenciesTypes: List<QuotesType.CurrenciesType>
    ): Result<List<ExchangeRateInfo>, Nothing> = withContext(dispatchersProvider.io) {
        requestWrapper.wrap {
            val result = basicApi.get(
                baseUrl = "https://www.cbr-xml-daily.ru/",
                path = "daily_json.js",
                resultClass = CurrencyResponseJson::class.java
            )
            result?.asDomain(currenciesTypes) ?: emptyList()
        }
    }

    private fun Map<String, QuotationJson>.asDomain(timeZone: String) =
        map {
            Quotation(
                dateTime = ZonedDateTimeUtils.parse2(it.key, timeZone),
                open = it.value.open.toFloat(),
                close = it.value.close.toFloat(),
                high = it.value.high.toFloat(),
                low = it.value.low.toFloat(),
                volume = 0f,
            )
        }

    private fun CurrencyResponseJson.asDomain(currenciesTypes: List<QuotesType.CurrenciesType>): List<ExchangeRateInfo> {
        val currencies = currenciesTypes.associateBy { it.from }
        return currencyExchangeRate.filter { currencies.keys.contains(it.key) }
            .mapNotNull {
                val type = currencies[it.key]
                if (type != null) {
                    ExchangeRateInfo(
                        type = QuotesType.Currencies(type),
                        fromCode = it.value.code,
                        fromName = it.value.name,
                        toCode = "RUB",
                        toName = "Российский рубль",
                        exchangeRate = it.value.value,
                        lastRefresh = ZonedDateTimeUtils.parse(date)
                    )
                } else null
            }
    }

}