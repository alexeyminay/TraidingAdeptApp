package com.alexey.minay.core_remote

object BasicApiFactory {

    fun create() = BasicApi(
        baseUrl = "https://www.alphavantage.co",
        httpClient = OkHttpFactory.create(),
        apiKey = BuildConfig.API_KEY,
        moshi = MoshiFactory.create()
    )

}