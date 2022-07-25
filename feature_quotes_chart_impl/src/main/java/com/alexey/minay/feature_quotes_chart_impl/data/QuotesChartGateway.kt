package com.alexey.minay.feature_quotes_chart_impl.data

import com.alexey.minay.feature_quotes_chart_impl.BuildConfig
import com.alexey.minay.feature_quotes_chart_impl.domain.Quotation
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.ZonedDateTime

class QuotesChartGateway {

    val client = OkHttpClient()

    val moshi = Moshi.Builder()
        .add(ZonedDateTimeAdapter())
        .build()

    suspend fun getQuotes() = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey=${BuildConfig.API_KEY}")
            .build()

        try {
            val response = client.newCall(request)
                .execute()

            val adapter = moshi.adapter(QuotesChartResponseJson::class.java)

            return@withContext adapter.fromJson(response.body?.string())?.timeSeries?.asDomain()
        } catch (e: Exception) {
            e.printStackTrace()
            null
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