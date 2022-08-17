package com.alexey.minay.core_remote

interface IBasicApi {
    fun <T> get(
        baseUrl: String? = null,
        path: String,
        resultClass: Class<T>,
        query: Map<String, String>? = null
    ): T?
}