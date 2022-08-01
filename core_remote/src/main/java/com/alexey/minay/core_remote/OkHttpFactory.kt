package com.alexey.minay.core_remote

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient

object OkHttpFactory {

    fun create() =
        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

}