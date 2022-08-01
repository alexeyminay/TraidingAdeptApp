package com.alexey.minay.core_remote

interface IBasicApi {
    fun <T> get(
        path: String,
        resultClass: Class<T>,
        query: Map<String, String>?
    ): T?
}