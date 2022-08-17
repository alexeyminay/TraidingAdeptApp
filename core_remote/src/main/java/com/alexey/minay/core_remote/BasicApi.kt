package com.alexey.minay.core_remote

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request

class BasicApi constructor(
    private val baseUrl: String,
    private val httpClient: OkHttpClient,
    private val apiKey: String,
    private val moshi: Moshi
) : IBasicApi {

    override fun <T> get(
        baseUrl: String?,
        path: String,
        resultClass: Class<T>,
        query: Map<String, String>?
    ): T? {
        val request = Request.Builder()
            .get()
            .url(createUrl(url = baseUrl ?: this.baseUrl, path, query))
            .build()

        val response = httpClient.newCall(request)
            .execute()

        val adapter = moshi.adapter(resultClass)
        return response.body?.string()?.let { adapter.fromJson(it) }
    }

    private fun createUrl(
        url: String,
        path: String,
        query: Map<String, String>?
    ): String {
        val queryStr = when (query) {
            null -> ""
            else -> "?${query.generateQueryRow()}"
        }

        return "$url/$path${queryStr}"
    }

    private fun Map<String, String>.generateQueryRow(): String {
        return asSequence()
            .mapIndexed { index, (key, value) ->
                val amp = when (index == size - 1) {
                    true -> ""
                    false -> "&"
                }
                "${key}=${value}$amp"
            }.fold("") { prev, next ->
                "$prev$next"
            }.addApiKeyQuery()
    }

    private fun String.addApiKeyQuery() =
        "$this&apikey=$apiKey"

}